# Warcraft Dungeoneer
[![Current Version](https://img.shields.io/badge/version-1.0-green.svg)](https://github.com/m9ciek/Warcraft-Tracker) <br/>

>Backend REST Api, which is dedicated for **World of Warcraft** players, it is based on Blizzard API to track stats and rankings for certain characters from the game, it also includes statistics from fanmade websites - [RaiderIO](https://raider.io/) and [Warcraft Logs](https://www.warcraftlogs.com/). <br/>
It calculates score for every completed dungeon and can be used to determine whether character has enough experience for mythic dungeon group. Only works for european realms!
### Technologies
* Java 11
* Spring Framework - Boot, Security
* OAuth 2.0
* Lombok
#### Tests
* JUnit 5
* Mockito

### General info
Website uses OAuth 2.0 Authorization - In order to be able to use warcraft tracker you need to have an **battle.net** account - [Blizzard Homepage](https://www.blizzard.com/)

## Setup
* Navigate to project root: `warcraft-stats-tracker` by default
* Build project with maven: `./mvnw clean install`
* Run the app: `./mvnw spring-boot:run`

## Features
* Check your character statistics and details
* Compare characters by their experience and mythic plus score
* Decide who you want in your **World of Warcraft** mythic group

## Usage
App by default starts on `localhost:8080`
#### Rest Endpoints:
* GET:<br/>
  * **Logged user data** - `/api/user-data`  
  * **Logged user World of Warcraft profile details** - `/api/user-data/wow-profile`  
  * **Logged user all characters on every realm** - `/api/user-data/user-characters`
  #####
  * **Get character detailed data** - `/api/character/{characterName}`  
  *Required param:* `realm` - type String  
  Example: `/api/character/druiss?realm=tarren-mill` 
  
  * **Get character mythic dungeon stats** - `/api/dungeoneer/data/{characterName}`  
  *Required params:* `realm` - type String, `season` - positive integer (1 - 4)  
  Example: `/api/dungeoneer/data/druiss?season=4&realm=tarren-mill`


##### Status: _in progress_

##### Created by [@m9ciek](https://www.linkedin.com/in/maciej-paszynski/) - feel free to contact me!
