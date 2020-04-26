# YallNotified

Plugin for Spigot/Bukkit servers, it allows you to set up Telegram (for now) notifications for specific events, when a user joins or quits the game, etc...

****
> ### NOTICE
> THIS PLUGIN IS UNDER DEVELOPMENT, IT MIGHT BE UNSTABLE OR FEATURES MIGHT CHANGE, **USE AT YOUR OWN RISK**.
****


## Features
_Items unchecked not yet implemented_

* **Notifiers:**
  - [x] Telegram Notifier
  - [ ] Webhook Notifier
  - [ ] Http Notifier
  - [ ] Custom Messages
* **Events:**
  - [x] On Player Join
  - [x] On Player Quit

> I only needed this events for now, if you would like more events to be supported please drop an [issue](https://github.com/nombrekeff/spigot-event-notifier/issues/new)


## Getting Started
1. Download [latest release]()
2. Copy `YallNotified.jar` to `server/plugins` folder
3. Restart server
4. A config file is generated under `server/plugins/YallNotified/config.yml`, see [config](#config).


## Config
```yaml
# config.yml
telegram:                   # Config for Telegram notifier
    enabled: false          # enable this notifier
    token: TELEGRAM_TOKEN   # telegram bot token
    chat_ids: []            # list of chat ids to wich to notify
    events:                 # list of events (you can enable/disable any events)
        onJoin: true            
        onQuit: true
```
