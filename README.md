# Ace of Shades Server Registry
The registry is a REST API that keeps track of any servers that have recently announced their status to it. Servers can periodically send a simple JSON object with metadata about the server (name, description, players, etc.) so that players can more easily search for a server to play on.

### Fetching
Client/launcher applications that want to get a list of servers from the registry should send a GET request to the API's `/servers` endpoint.

The following array of servers is returned from GET requests to the API's `/servers` endpoint:
```json
[
    {
        "host": "0:0:0:0:0:0:0:1",
        "port": 1234,
        "name": "Andrew's Server",
        "description": "A good server.",
        "maxPlayers": 32,
        "currentPlayers": 2,
        "lastUpdatedAt": 1659710488855
    }
]
```

### Posting
Servers should regularly send their information to the registry, to keep their information up-to-date. The following payload should be sent by servers to the API's `/servers` endpoint via POST:
```json
{
    "port": 1234,
    "name": "Andrew's Server",
    "description": "A good server.",
    "maxPlayers": 32,
    "currentPlayers": 2
}
```
Note that this should only be done at most once per 30 seconds. Any more frequent, and you'll receive 429 Too-Many-Requests responses, and continued spam may permanently block your server.

All servers **must** provide a token via the `X-AOS2-REGISTRY-TOKEN` header. This token must be valid for the registry to acknowledge the server's requests, or a 401 Unauthorized response is given.

#### On Server Shutdown
Servers can announce to the registry that they're shutting down, in which case the registry can immediately remove them, instead of waiting a few minutes for the server to time out. Servers should send a POST request to `/servers/shutdown` with the following payload:
```json
{
  "port": 1234
}
```

## Development
This registry is built using Spring-Webflux for a reactive, non-blocking design.

The `ServerRegistry` component is the heart of the system, and contains an internal list of servers, mapped by their unique hostname and port number. The registry periodically removes servers from this list which haven't sent an update for a while. When servers post their information, it overrides any existing information for that server.
