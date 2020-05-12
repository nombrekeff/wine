# YallNotified

Plugin for Spigot/Bukkit servers, it allows you to set up Telegram (for now) notifications for specific events, when a user joins or quits the game, etc...

****
> ### NOTICE
> THIS PLUGIN IS UNDER DEVELOPMENT, IT MIGHT BE UNSTABLE OR FEATURES MIGHT CHANGE, **USE AT YOUR OWN RISK**.
****


## Features
_Items unchecked not yet implemented_

* **General:**
- [x] Disable ALL notifications for certain players
- [x] Disable individual event notifications for certain players

* **Notifiers:**
  - [x] Telegram Notifier
  - [ ] Discord Notifier
  - [ ] Webhook Notifier
  - [x] Custom Messages
* **Events:**
  - [x] On Player Join
  - [x] On Player Quit
  - [x] On Player Death

> I only needed these events for now, if you would like more events to be supported please drop an [issue](https://github.com/nombrekeff/spigot-event-notifier/issues/new)


## Getting Started
1. Download [latest release](https://github.com/nombrekeff/spigot-event-notifier/releases)
2. Copy `YallNotified.jar` to `server/plugins` folder
3. Restart server
4. A config file is generated under `server/plugins/YallNotified/config.yml`, see [config](#config).


## Config
Basic config, see [config.yml](./src/main/resources/config.yml) for more detailed info.

```yaml
# YallNotified/config.yml

# Config for plugin
update_checker: true

# ignores events for players and entities (for all notifiers)
ignored_players:
  - nombrekeff

telegram:
  enabled: false

  # Your telegram bot token
  token: TELEGRAM_TOKEN

  # List of chats to which to notify
  chat_ids: []
  
  # ignores events for players and entities (for this notifiers)
  ignored_players: 
    - nombrekeff

  # List of events this notifier will send
  events:
    PlayerJoinEvent: 
      format: '{name}, joined the server!'
    PlayerQuitEvent: 
      enabled: false
      format: '{name}, left the server!'
    PlayerDeathEvent: 
      format: '{name} died in {world}, at x: {death_x} y: {death_y} z: {death_z} {death_cause}'
```
