# AutoCommandsPlus 1.1.8
Powerful bukkit/spigot scheduler.
It can run commands in interval.
It can run commands to all players on the server, or random player, or everyone excluding random player.
It can run commands as player, as randomplayer.

Even it's not just a commands scheduler.
You can use it as message scheduler! (it's fully supported)
Just put your message like command:
```yaml
commands: 
- "[YourPrefix] Hello everyone!"
- "It's message"
- "$>Random player is %randomplayer%"
- "Hey, %player%, do you know that last random player was %randomplayer%"
- "!>Only you, last random player %randomplayer%, hear this!"
- "$!>Only you, new random player %randomplayer%, hear this!"
- "&d&l[Prefix] &b&lIt's colored message. &f&lAnd\nthis is new line. More examples in "allparameters" list in the config file."
```

Powerful. Free. Opensource.
Enjoy!

<<<<<<< HEAD
Let's look through the config (I tested everything, but plugin is beta, so use tickets if you encounter a bug):
=======
Let's look through the config (plugin is beta, so 80% of this don't work for now):
>>>>>>> da92b5a64dc71de0e0234d4015ab40f0cfc63d06
```yaml
# /ac aliases: /autocommands, /acp, /autocommandsplus
#
# COMMANDS:
# /ac reload
# /ac set <listname> <enabled/disabled>
# /tellp (alias is /raw) (it's cool! Especially for command blocks)
#
# IMPORTANT:
# Random players are different for every list!
# Command '/ac set ...' removes all comments
#   and shuffles the variables in this file :(
# I recommend you to enable/disable lists here yourself
#   and use /ac reload. 
#
# PLACEHOLDERS:
# %player%, %playername% are the same, but %playername% shows player's displayname (with prefixes)
# Use %player% placeholder in commands, and %playername% in messages
# %randomplayer%, %randomplayername% (see above)
# %online%, %maxplayers%
# %balance% (requires Vault)

# you can enable or disable whole plugin by changing this and running /ac reload
enabled: true
# use it if you want to see a lot of strange messages in your console
debug: false
lists:
  simple:
    enabled: true
    interval: 120
    type: random
    minimumPlayers: 2
    permission: messages.receive
<<<<<<< HEAD
    commands:
    - '&e&l[AutoCommandsPlus] &fDonate to us! &b&l/donate'
    - '&e&l[AutoCommandsPlus] &fEnjoy our minigames: &b&l/minigames'
    - '&e&l[AutoCommandsPlus] &fWant to relax? Write &b&l/warp beach'
  clearmemory:
    enabled: false
    interval: 300
    type: default
    minimumPlayers: 0
    permission: none
    commands:
    - '&bAll items on ground will be removed in 5 minutes!'
    - '/lagg clear>;&bAll items on ground have been removed.'
    - '/lagg unloadchunks>;/lagg gc>;&bMemory has been cleaned!'
  allparameters:
    enabled: false
    # 60 seconds = 1 minute, 3600 seconds = 1 hour, 86400 seconds = 1 day
    interval: 60
    # only default is supported now
    type: default
    # if bigger than 0, commands will be ran only if there are minimum players on the server
    minimumPlayers: 0
    # player will need this permission to 'hear'/run the commands or messages (starting with '@') with the player
    permission: none
    commands:
    # if you want to just send message to all players, put it as a command (without / or something)
    # in message you can use such parameters as $ and !
    # you can run multiple commands using >;
=======
    commands:
    - '&e&l[AutoCommandsPlus] &fDonate to us! &b&l/donate'
    - '&e&l[AutoCommandsPlus] &fEnjoy our minigames: &b&l/minigames'
    - '&e&l[AutoCommandsPlus] &fWant to relax? Write &b&l/warp beach'
  clearmemory:
    enabled: false
    interval: 300
    type: default
    minimumPlayers: 0
    permission: none
    commands:
    - '&bAll items on ground will be removed in 5 minutes!'
    - '/lagg clear>;&bAll items on ground have been removed.'
    - '/lagg unloadchunks>;/lagg gc>;&bMemory has been cleaned!'
  allparameters:
    enabled: false
    # 60 seconds = 1 minute, 3600 seconds = 1 hour, 86400 seconds = 1 day
    interval: 60
    # only default is supported now
    type: default
    # if bigger than 0, commands will be ran only if there are minimum players on the server
    minimumPlayers: 0
    # player will need this permission to 'hear'/run the commands or messages (starting with '@') with the player
    permission: none
    commands:
    # if you want to just send message to all players, put it as a command (without / or something)
    # in message you can use such parameters as $ and !
    # you can run multiple commands using >;
    # in this example first command ran, then wait for 2 seconds and then run second command
>>>>>>> da92b5a64dc71de0e0234d4015ab40f0cfc63d06
    # '@' runs command to every player (you can use %player% placeholder)
    # '$' reselects random player (placeholder is %randomplayer%)
    # '!' runs command or shows the message only to random player
    # you can also use %randomplayer% after the command with $-prefix (every command with $-prefix will reselect(!) random player)
    # '#' runs command as player (use with '@' or '!')
    # '-' excludes random player from '@'
    # %randomplayername% and %playername% are display names with colors and prefixes (if used)
    - "@>/title %player% subtitle \"&lRandom player is &b&l%randomplayer%\"\
      >;\
      @>/title %player% title \"&lYour name is &b&l%player%\"\
      >;\
      %player%, we've shown you a title! Cookoo :D"
    - "Hi, %player%! It's just a message :) &bYou can use color codes, &emake\nmultiline messages."
    - "It's long message. \
      You can write it and write it (it's not multiline)."
    - |-
      It's multiline message. Every line here is new line in game.
      Woohoo!
      Isn't it nice?
    - "$!>Hey, %randomplayer%, only you can see this message."
    - "!>Hey, %randomplayer%, it's me again!"
    - "Omg, %randomplayer% is ignoring me! >:(>;$>Okay, i'm going to disturb %randomplayer% now.."
    - "-@>This message sees everyone excluding %randomplayer%."
    - "Placeholders: %player% (and %playername%), %randomplayer% (and %randomplayername%), \
      %online%/%maxplayers%, %balance%"
    - "And now I'm going to show you top of balances!>;@#>/balancetop"
    - "$!>%randomplayer%, i want you to give money to the admin ;)>;!#>/pay admin 500"
    - "$!#>/me is stupid! Or not..."
    - "-@#>/tell %randomplayer% Woop woop. Yep, we've all sent a message to you, %randomplayername%."
# Supports UTF-8 text
locale:
  noPermission: '&a&l[AutoCommandsPlus]&r &cYou don''t have enough permissions for this!'
  wrongTellp: '&a&l[AutoCommandsPlus]&r &fUsage&7: &b/tellp <player> <message>'
  pluginReloaded: '&a&l[AutoCommandsPlus]&r &fPlugin has been successfully reloaded!'
  wrongEnabled: '&a&l[AutoCommandsPlus]&r &fUsage&7: &b/ac enabled <listname> <true/false>'
  listEnabledChanged: '&a&l[AutoCommandsPlus]&r &fList &b%list%&f is now &b%enabled%&f.'
  enabledTrue: 'enabled'
  enabledFalse: 'disabled'
  help: '&a&l[AutoCommandsPlus]&r &fAvailable commands: &b/ac reload&f, &b/ac enabled <list> <true/false>&f, &b/tellp'
  version: "&a&l[AutoCommandsPlus]&r &fVersion of the plugin: &b&l"
```
