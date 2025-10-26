# Template Mod

A Minecraft Fabric mod demonstrating custom network messaging and PostgreSQL integration.

## 📦 Download

**Latest Release:** [v1.0.0](https://github.com/antlau2000/template-mod/releases/latest)  
*Requires Fabric API*

## ✨ Features
- 📨 Custom GUI for sending messages (Press `M`)
- 🌐 Network communication using Fabric API  
- 🗄️ PostgreSQL database storage
- 🔧 Client-server architecture
- 💾 Persistent message storage

## 🛠 Requirements
- **Minecraft:** 1.21.8
- **Fabric Loader:** 0.17.2+
- **Fabric API:** [Download here](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
- **PostgreSQL** (for server)

## 🚀 Installation

### Client
1. Install [Fabric Loader](https://fabricmc.net/use/)
2. Download [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
3. Download latest release from [Releases page](https://github.com/antlau2000/template-mod/releases)
4. Place both mods in Minecraft `mods/` folder

### Server  
1. Setup [Fabric Server](https://fabricmc.net/use/server/)
2. Configure PostgreSQL database:
   ```sql
   CREATE DATABASE minecraft;
   CREATE TABLE messages (
       id BIGSERIAL PRIMARY KEY,
       uuid UUID NOT NULL,
       text VARCHAR(256) NOT NULL
   );
