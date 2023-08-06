# Data Never Requested or Stored
- email address(es)
- first and/or last name
- home or work address(es)
- postal code
- state/province
- ANY logins or passwords to literally anything

# Data From Discord
- `Global Username`
  - *Stored in the Member identity reference table - Holds reference to Elite Account info*
- `Discord ID`
  - *Stored in the Member identity reference table - Holds reference to Elite Account info*

# Data From Frontier Authorization
- `refresh_token`
  - *So updated information can be retrieved later on without prompting another login request*
  - *`refresh_token` is cryptographically encoded with a private key-store prior to being stored in the database, then decoded using the public key-store after retrieving from the database*
    - *For security purposes, neither the encoded/decoded `refresh_token` can be retrieved by users.*
    - *The `access_code` itself is never stored to the database*

# Data from Frontier CAPI Endpoints
- `/profile`
  - *commander.name*
    - *Stored in the Member identity reference table - Holds reference to Discord Account info*
  - *commander.id*
    - *Stored in the Member identity reference table - Holds reference to Discord Account info*
  - see [Athanasius/fd-api#profile](https://github.com/Athanasius/fd-api/blob/main/docs/FrontierDevelopments-CAPI-endpoints.md#profile) 
- `/fleetcarrier`
  - *Because of the changing features of the service, we do not store all information returned from this endpoint at this time. But may, in the future, store all returned information*
  - see [Athanasius/fd-api#fleetcarrier](https://github.com/Athanasius/fd-api/blob/main/docs/FrontierDevelopments-CAPI-endpoints.md#fleetcarrier) 
