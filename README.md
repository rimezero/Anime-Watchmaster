# Anime-Watchmaster
-------------
![Lang](https://img.shields.io/badge/Language-Java-green)
![Using](https://img.shields.io/badge/Using-SQLite-green)
![Scrapper](https://img.shields.io/badge/-WebScrapper-blue)
![Anime](https://img.shields.io/badge/-Anime-red)

Client written in JAVA-FX. Follows a classic client server with an API architecture. <br/>
Contains web scrappers to scrap live data from external servers using Jsoup  <br/><br/>
Anime Watchmaster is a free open source app for anime lovers. It offers an <br/>
easy and user friendly way to browse and keep track of your favourite ongoing anime. <br/>
From your watchlist find all new episodes with one click. Keep track of your watched <br/>
episodes, download and watch episodes in the best quality without having to keep track <br/>
of links and files in your file system. See all the information you need to watch anime<br/>
in a single compact window.
<br/><br/>
**Disclaimer:** This app only provides links to external servers. Does not contain and/or
distribute content of any kind. 
<br/><br/>

## Usage
-----
### How to download and watch anime using AnimeWatchmaster
#### Setup new anime in watchlist
- From the main app window open options>Configuration and choose a default directory for downloads (optional) and save config
- Browse using the all anime or seasons tabs to find desired anime
- Click show on the anime in the list then click add to watchlist
- Open watchlist from the main window
- Click show button for the anime then click download. Opens nyaa page on your default browser searching for said anime
- From nyaa copy desired torrent prefix example [SubsPlease] Spy x Family
- Scroll the watchlist all the way to the right and double click to edit torrent name. Paste copied prefix and press enter
- Create folder on your file system and paste path on save path column or leave blank to automatically create folder with anime title in default directory (set in config)
- Click Update (black button that appears after setting torrent name). Automatically finds all available episodes.
- Click Download Next - Your default torrent opens the download tracker. Choose the folder set in previous steps. Wait for torrent to download. 
- Click Watch Next - Your default video player plays the next episode. 
- After watchling double click episodes watched number in the watchlist to manually change it. 
- After first setup it is just open Watchlist > Update > Download Next > Watch next

#### Return after a while and click update watchlist to see if new episodes are out

## Images
![Watchlist](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/watchlist.png)
![WatchlistR](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/watchlistR.png)
![AllAnime](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/allAnime.png)
![Seasons](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/seasons.png)
![Animeinfo](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/animeinfo.png)
![Filters](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/filters.png)
![Configuration](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/configuration.png)
![MyList](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/myList.png)
![MainWindow](https://github.com/CsPeitch/Anime-Watchmaster/blob/master/src/animeApp/assets/icons/mainWindow.png)

## LICENSE
----
BSD 2-Clause License

Copyright (c) 2022, Chrysovalantis Pitsas
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
