/*
 * This file is part of JDATools, licensed under the MIT License (MIT).
 *
 * Copyright (c) ROMVoid95
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package space.tscg.internal.server.bus;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;

import space.tscg.misc.Conditional;

public class ServerEventWaiter implements ServerEventListener
{

	private static final Logger							LOG	= LoggerFactory.getLogger(ServerEventWaiter.class);
	@SuppressWarnings("rawtypes")
	private final HashMap<Class<?>, Set<WaitingEvent>>	waitingEvents;
	private final ScheduledExecutorService				threadpool;
	private final boolean								shutdownAutomatically;

	/**
	 * Constructs an empty EventWaiter.
	 */
	public ServerEventWaiter()
	{
		this(Executors.newSingleThreadScheduledExecutor(), true);
	}

	public ServerEventWaiter(ScheduledExecutorService threadpool, boolean shutdownAutomatically)
	{
	    Checker.notNull(threadpool, "ScheduledExecutorService");
		Checker.check(!threadpool.isShutdown(), "Cannot construct EventWaiter with a closed ScheduledExecutorService!");

		this.waitingEvents = new HashMap<>();
		this.threadpool = threadpool;

		this.shutdownAutomatically = shutdownAutomatically;
		ServerEventManager.registerListener(this);
	}

	public boolean isShutdown()
	{
		return threadpool.isShutdown();
	}
	
    public <T extends BaseEvent> void waitForEvent(Class<T> classType, Consumer<T> action)
    {
        waitForEvent(classType, Conditional.instanceOf(classType), action, 5, TimeUnit.MINUTES, null);
    }

	public <T extends BaseEvent> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action)
	{
		waitForEvent(classType, condition, action, 5, TimeUnit.MINUTES, null);
	}

	@SuppressWarnings("rawtypes")
	public <T extends BaseEvent> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action, long timeout, TimeUnit unit, Runnable timeoutAction)
	{
	    Checker.check(!isShutdown(), "Attempted to register a WaitingEvent while the EventWaiter's threadpool was already shut down!");
	    Checker.notNull(classType, "The provided class type");
	    Checker.notNull(condition, "The provided condition predicate");
	    Checker.notNull(action, "The provided action consumer");

		WaitingEvent		we	= new WaitingEvent<>(condition, action);
		Set<WaitingEvent>	set	= waitingEvents.computeIfAbsent(classType, c -> ConcurrentHashMap.newKeySet());
		set.add(we);
		if ((timeout > 0) && (unit != null))
		{
			threadpool.schedule(() ->
			{
				try
				{
					if (set.remove(we) && (timeoutAction != null))
					{
						timeoutAction.run();
					}
				} catch (Exception ex)
				{
					LOG.error("Failed to run timeoutAction", ex);
				}
			}, timeout, unit);
		}
	}

	public <T extends BaseEvent> void runEvent(long timeout, TimeUnit unit, Runnable timeoutAction)
	{
		Checker.check(!isShutdown(), "Attempted to register a WaitingEvent while the EventWaiter's threadpool was already shut down!");
		if ((timeout > 0) && (unit != null))
		{
			threadpool.schedule(() ->
			{
				try
				{
					if (timeoutAction != null)
					{
						timeoutAction.run();
					}
				} catch (Exception ex)
				{
					LOG.error("Failed to run timeoutAction", ex);
				}
			}, timeout, unit);
		}
	}

	@SuppressWarnings(
		{ "rawtypes", "unchecked" })
	@Override
	@Subscribe
	public final void onEvent(BaseEvent event)
	{
		Class c = event.getClass();

		while (c != null)
		{
			final Set<WaitingEvent> set = waitingEvents.get(c);
			if (set != null)
			{
				set.removeIf(wEvent -> wEvent.attempt(event));
			}

			c = c.getSuperclass();
		}
	}

	/**
	 * Closes this EventWaiter if it doesn't normally shutdown automatically.
	 * <p>
	 * <b>IF YOU USED THE DEFAULT CONSTRUCTOR WITH NO ARGUMENTS DO NOT CALL THIS!</b> <br>
	 * Calling this method on an EventWaiter that does shutdown automatically will result in an
	 * {@link java.lang.UnsupportedOperationException UnsupportedOperationException} being thrown.
	 *
	 * @throws UnsupportedOperationException
	 *             The EventWaiter is supposed to close automatically.
	 */
	public void shutdown()
	{
		if (shutdownAutomatically)
		{
			throw new UnsupportedOperationException("Shutting down EventWaiters that are set to automatically close is unsupported!");
		}
		threadpool.shutdown();
	}

	private static class WaitingEvent<T extends BaseEvent>
	{
		final Predicate<T>	condition;
		final Consumer<T>	action;

		WaitingEvent(Predicate<T> condition, Consumer<T> action)
		{
			this.condition = condition;
			this.action = action;
		}

		boolean attempt(T event)
		{
			if (condition.test(event))
			{
				action.accept(event);
				return true;
			}
			return false;
		}
	}
	
	private static final class Checker {
	    public static void check(final boolean expression, final String message)
	    {
	        if (!expression)
	            throw new IllegalArgumentException(message);
	    }
	    
	    public static void notNull(final Object argument, final String name)
	    {
	        if (argument == null)
	            throw new IllegalArgumentException(name + " may not be null");
	    }
	}
}
