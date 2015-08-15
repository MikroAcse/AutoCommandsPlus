# AutoCommandsPlus
Powerful bukkit/spigot scheduler.
It can run commands in interval, with delay.
It can run commands using all players on the server, or just random player.
It can run commands as player.

Even it's not just a commands scheduler.
You can use it as message scheduler.

Powerful. Free. Opensource.
Enjoy!

Let's look through the config (plugin is beta, so 80% of this don't work for now):
lists:
  simple:
    interval: 300
    permission: none
    type: default
    commands:
    - '/say Hi everyone!'
    - '/lagg gc'
    - '/lagg unloadchunks'
    - '/tm bc Buy our donate!'

  # will be available soon
  _first:
    # 300 seconds = 5 minutes
    interval: 300
    # default or random are supported (soon non-repeated random)
    type: default
    # player needs this permission to run the commands (starting with '@') with the player
    permission: none
    commands:
    # you can separate commands by >;
    # you can timeout commands by separating >;2s;> where 2s = 2 seconds
    # in this example first command ran, then wait for 2 seconds and then run second command
    # '@' means that command will be ran with every player (you can use %player% placeholder)
    # '$' means the same as '@', but it takes only one random player
    # '#' means that command will be ran 'sudo' player
    # random player will be the same in the one line (but you can change it, write ';rand;' that will reselect random player)
    # '-' excludes random player from '@'
    - '@/tell %player% Hi!>;2s>;@/tell %player% 2 seconds passed'
    - '/tm msg Donate to us! /donate'
    - '$#/pay admin 1000>;$/tell %randplayer% You won the lottery!>;-@/tell %player% %randplayer% won the lottery!'
    - '$/tell %randplayer% Wow! You are the first!>;rand>;$/tell %randplayer% Wow! You are the second!'
  _second:
    interval: 60
    permission: autocommands.admin
    type: random
    commands:
    - '@/tell %player% Hello, admin!'

locale:
  noPerm: 'You don''t have enough permissions for this!'