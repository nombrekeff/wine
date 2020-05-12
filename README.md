# Wine - Spigot Notifier

Spigot/bukkit notifier plugin, you can send messages to **Telegram**, notify against a **Webhook** or to **Discord** servers (WIP). **Customize messages**, enable/disable events for certain users, or mute events as you please. Adds a set of utility commands for simpler management.

****
> ### NOTICE
> Plugin in early stages, is mostly stable but some features might be missing. 
****

## Features
_Items unchecked not yet implemented_

* **General:**
- [x] Disable ALL notifications for certain players
- [x] Disable individual event notifications for certain players
* **Notifiers:**
  - [x] Telegram Notifier
  - [x] Custom Messages
  - [ ] Webhook Notifier <kbd>usable, needs work</kbd>
  - [ ] Discord Notifier <kbd>WIP</kbd>
* **Events:**
  - [x] On Player Join
  - [x] On Player Quit
  - [x] On Player Death

> I only needed these events for now, if you would like more events to be supported please drop an [issue](https://github.com/nombrekeff/wine/issues/new)


## Getting Started
1. Download [latest release](https://github.com/nombrekeff/wine/releases)
2. Copy `Wine.jar` to `server/plugins` folder
3. Restart server
4. A config file is generated under `server/plugins/Wine/config.yml`, see [config](#config).

##Â Commands
* `/yn <ignore|unignore> <player> [notifier]` - ignore events for user, for notifier or in general if notifier is omitted

##Â Permissions
* `yn.commands` allows a player to execute commands

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
