# Bomb Lobbers Plugin

My take on the Mineplex bomb lobbers plugin, but even better!  
Toss bombs at your friends, and try to blow up the ground below them, or knock them off into the water!

(Map not included)

# Features

-   Right click with TNT to toss it
-   Game system:
    -   Three working teams! (Blue, red, and green)
    -   Supports 2 – infinity players
    -   Water damages players during a game 
    -   Players receive a TNT every 5 seconds
    -   Cooldown on TNT throws to every 3 seconds
    -   2 stacks of scaffolding and 3 ender pearls are given to players each game
    -   Players are put into spectator mode after dying (no respawn screen)
    -   Countdown to game start
    -   Game ends when only one team remains
    -   Game over title, displaying what team won the game
-   **Direct hit detection**
    -   If a player is hit directly with a TNT, they will get damaged and go flying
    -   "Ding" noise so the thrower knows if it hit

# Commands & Usage

-   **/bomblobbers** (alias **/bl**): Toggles the plugin
    -   **enable**: enables the plugin
    -   **disable**: disables the plugin  
    -   **start**: starts a game
    -   **stop**: forces a game to end
    -   **changeteam**: GUI to choose between blue, red, and green team
-   Set the world spawnpoint to where you want the spectators to respawn after dying

# In development:

-   Customization:
    -   Custom TNT velocity
    -   Custom time between every TNT
    -   Custom cool down for throwing TNT
    -   Custom direct hit velocity
    -   Custom direct hit damage
    -   Custom water damage per second
    -   Custom countdown amount
    -   Custom items at the start of the game
-   Bug fixes:
    -   Make it so after dying, spectators will only respawn at world spawn point if fallen out of the world
    -   Make it so the game checks more frequently (than every 5 seconds) which team won
-   Add a fourth team

Please report issues to the [issues section](https://github.com/JohnnnnyKlayy/BombLobbers/issues).  
  
This project is open-source.

Check out the [spigot page for this plugin](https://www.spigotmc.org/resources/bomb-lobbers.82305/)!
