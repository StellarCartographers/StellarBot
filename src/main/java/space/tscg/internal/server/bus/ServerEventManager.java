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

import com.google.common.eventbus.EventBus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServerEventManager
{
    private volatile static ServerEventManager instance;
    private volatile EventBus                  eventBus;
    private volatile ServerEventsProxy         eventsProxy;

    static ServerEventManager getInstance()
    {
        if (instance == null)
        {
            synchronized (ServerEventManager.class)
            {
                if (instance == null)
                {
                    instance = new ServerEventManager(new EventBus("ServerEventManager"), new ServerEventsProxy());
                }
            }
        }
        return instance;
    }
    
    public static ServerEventsProxy getEventsProxy()
    {
        return getInstance().eventsProxy;
    }

    public static void registerListener(Object listener)
    {
        getInstance().eventBus.register(listener);
    }

    void postEvent(BaseEvent event)
    {
        getInstance().eventBus.post(event);
    }
}
