# YallNotified

Plugin for Spigot/Bukkit servers, it allows you to set up Telegram (for now) notifications for specific events, when a user joins or quits the game, etc...

****
> ### NOTICE
> THIS PLUGIN IS UNDER DEVELOPMENT, IT MIGHT BE UNSTABLE OR FEATURES MIGHT CHANGE, **USE AT YOUR OWN RISK**.
****


## Getting Started
1. Download [latest release]()
2. Copy `YallNotified.jar` to `server/plugins` folder
3. Restart server
4. A config file is generated under `server/plugins/YallNotified/config.yml`, see [config]().


## Config
```yaml
# Config for Telegram notifier
telegram: 
    enabled: false              # enable this notifier
    token: TELEGRAM_TOKEN       # telegram bot token
    chat_ids: []                # list of chat ids to wich to notify
    events:                     # list of events (you can enable/disable any events)
        onJoin: true            
        onQuit: true
```