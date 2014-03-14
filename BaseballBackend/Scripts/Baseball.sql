DROP DATABASE IF EXISTS Baseball;
CREATE DATABASE Baseball;

USE Baseball;

CREATE TABLE Team
(
    team_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    teamname varchar(100) UNIQUE NOT NULL,
	money int NOT NULL
);

CREATE TABLE Position
(
    pos_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    pos_name varchar(100) UNIQUE NOT NULL,
	sortOrder int UNIQUE NOT NULL
);

CREATE TABLE bTeam
(
	bteam_id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    bteam_name varchar(100) UNIQUE NOT NULL,
	image varchar(400)
);

CREATE TABLE Player
(
	pid int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    player_name varchar(100) UNIQUE NOT NULL,
	bteam_id int,
	CONSTRAINT FOREIGN KEY (bteam_id) REFERENCES bTeam(bteam_id)  ON DELETE CASCADE
);

CREATE TABLE CanPlay
(
	pid int,
    pos_id int,
	CONSTRAINT FOREIGN KEY (pid) REFERENCES Player(pid)  ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (pos_id) REFERENCES Baseball.Position(pos_id)  ON DELETE CASCADE
);

CREATE TABLE Owns
(
    team_id int,
    pid   int,
	cost  int,
	seasonsHeld int,
	pos_id int,
    CONSTRAINT FOREIGN KEY (team_id) REFERENCES Team(team_id)  ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (pos_id) REFERENCES Baseball.Position(pos_id)  ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (pid) REFERENCES Player(pid) ON DELETE CASCADE
);

CREATE TABLE Bid
(
	team_id int,
    pid   int,
	bid int, 
    #CONSTRAINT FOREIGN KEY (team_id) REFERENCES Team(team_id)  ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (pid) REFERENCES Player(pid) ON DELETE CASCADE
);


INSERT INTO Team(teamname, money) VALUES ('Ball Beaters', 260);
INSERT INTO Team(teamname, money) VALUES ('Fish Fry', 260);
INSERT INTO Team(teamname, money) VALUES ('Freakin Bears', 260);
INSERT INTO Team(teamname, money) VALUES ('Mazur', 260);
INSERT INTO Team(teamname, money) VALUES ('Mike & Ike', 260);
INSERT INTO Team(teamname, money) VALUES ('On the Prowl for a Ginger', 260);
INSERT INTO Team(teamname, money) VALUES ('YOLO', 260);
INSERT INTO Team(teamname, money) VALUES ('Rhodes Kill', 260);
INSERT INTO Team(teamname, money) VALUES ("Schrute's Brutes", 260);
INSERT INTO Team(teamname, money) VALUES ('Sterne Image', 260);
INSERT INTO Team(teamname, money) VALUES ('Washington', 260);
INSERT INTO Team(teamname, money) VALUES ('Washinton 2.0', 260);

INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('C', 1);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('1B', 2);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('2B', 3);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('3B', 4);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('SS', 5);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('CM', 6);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('MI', 7);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('OF', 8);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('Ut', 9);
INSERT INTO Baseball.Position(pos_name, sortOrder) VALUES ('P', 10);

#American League Teams
#Insert Into bTeam(bteam_name, image) VALUES ('Baltimore Orioles', 	'http://mlb.com/mlb/images/team_logos/social_media/og/bal.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Boston Red Sox', 	  	'http://mlb.com/mlb/images/team_logos/social_media/og/bos.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Chicago White Sox', 	'http://mlb.com/mlb/images/team_logos/social_media/og/cws.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Cleveland Indians', 	'http://mlb.com/mlb/images/team_logos/social_media/og/cle.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Detroit Tigers',    	'http://mlb.com/mlb/images/team_logos/social_media/og/det.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Huston Astros', 		'http://mlb.com/mlb/images/team_logos/social_media/og/hou.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Los Angeles Angels', 	'http://mlb.com/mlb/images/team_logos/social_media/og/ana.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Minnesota Twins', 	'http://mlb.com/mlb/images/team_logos/social_media/og/min.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('New York Yankees', 	'http://mlb.com/mlb/images/team_logos/social_media/og/nyy.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Okland Athletics', 	'http://mlb.com/mlb/images/team_logos/social_media/og/oak.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Seattle Mariners', 	'http://mlb.com/mlb/images/team_logos/social_media/og/sea.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Tampa Bay Rays', 		'http://mlb.com/mlb/images/team_logos/social_media/og/tb.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Texas Rangers', 		'http://mlb.com/mlb/images/team_logos/social_media/og/tex.jpg');
#Insert Into bTeam(bteam_name, image) VALUES ('Toronto Blue Jays', 	'http://mlb.com/mlb/images/team_logos/social_media/og/tor.jpg');

#National League Teams
Insert Into bTeam(bteam_name, image) VALUES ('Arizona Diamondbacks',		'http://mlb.com/mlb/images/team_logos/social_media/og/ari.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Atlanta Braves', 				'http://mlb.com/mlb/images/team_logos/social_media/og/atl.jpg');	
Insert Into bTeam(bteam_name, image) VALUES ('Chicago Cubs', 				'http://mlb.com/mlb/images/team_logos/social_media/og/chc.jpg');	
Insert Into bTeam(bteam_name, image) VALUES ('Cincinnati Reds', 			'http://mlb.com/mlb/images/team_logos/social_media/og/cin.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Colorado Rockies', 			'http://mlb.com/mlb/images/team_logos/social_media/og/col.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Los Angeles Dodgers', 		'http://mlb.com/mlb/images/team_logos/social_media/og/la.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Miami Marlins', 				'http://mlb.com/mlb/images/team_logos/social_media/og/mia.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Milwaukee Brewers', 			'http://mlb.com/mlb/images/team_logos/social_media/og/mil.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('New York Mets', 				'http://mlb.com/mlb/images/team_logos/social_media/og/nym.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Philadelphia Phillies', 		'http://mlb.com/mlb/images/team_logos/social_media/og/phi.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Pittsburgh Pirates', 			'http://mlb.com/mlb/images/team_logos/social_media/og/pit.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('San Diego Padres', 			'http://mlb.com/mlb/images/team_logos/social_media/og/sd.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('San Francisco Giants', 		'http://mlb.com/mlb/images/team_logos/social_media/og/sf.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('St. Louis Cardinals', 		'http://mlb.com/mlb/images/team_logos/social_media/og/stl.jpg');
Insert Into bTeam(bteam_name, image) VALUES ('Washington Nationals',		'http://mlb.com/mlb/images/team_logos/social_media/og/was.jpg');
SELECT bteam_id Into @teamID FROM bTeam WHERE bteam_name = 'St. Louis Cardinals';
SELECT team_id Into @pteamID FROM team WHERE teamname = 'Sterne Image';

#C
Insert Into Player(player_name, bteam_id, pos_id) VALUES('Yadier Molina',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 2, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'C'));

#1B
Insert Into Player(player_name, bteam_id) VALUES('Matt Adams',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 7, 2);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = '1B'));

#2B
Insert Into Player(player_name, bteam_id) VALUES('Matt Carpenter',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 3, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = '2B'));

#3B
Insert Into Player(player_name, bteam_id) VALUES('Allen Craig',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 26, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = '3B'));

#SS
Insert Into Player(player_name, bteam_id) VALUES('Daniel Descalso',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 8, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'SS'));

#CM
Insert Into Player(player_name, bteam_id) VALUES('Greg Garcia',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 1, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'CM'));

#MI
Insert Into Player(player_name, bteam_id) VALUES('Pete Kozma',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 1, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'MI'));

#OF
Insert Into Player(player_name, bteam_id) VALUES('Peter Bourjos',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 15, 3);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'OF'));
Insert Into Player(player_name, bteam_id) VALUES('Joey Butler',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 46, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'OF'));
Insert Into Player(player_name, bteam_id) VALUES('Randal Grichuck',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 45, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'OF'));
Insert Into Player(player_name, bteam_id) VALUES('Matt Holiday',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 12, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'OF'));

#Ut
Insert Into Player(player_name, bteam_id) VALUES('John Jay',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 10, 0);
SET @pid = LAST_INSERT_ID();
Insert Into CanPlay(pid, pos_id) values( @pid, (select pos_id FROM Baseball.Position WHERE pos_name = 'Ut'));
Insert Into Player(player_name, bteam_id) VALUES("Mike O'Neill",@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 1, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'Ut'));

#P
Insert Into Player(player_name, bteam_id) VALUES('Joe Kelly',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 34, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Tyler Lyons',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 12, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Seth Maness',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 8, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Carlos Martinez',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 9, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Shelby Miller',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 8, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Trevor Rosenthal',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 4, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Michael Wacha',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 3, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Adam Wainwright',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 1, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));
Insert Into Player(player_name, bteam_id) VALUES('Keith Butler',@teamID);
Insert Into Owns(team_id, pid, cost, seasonsHeld) values ( @pteamID, LAST_INSERT_ID(), 2, 0);
Insert Into CanPlay(pid, pos_id) values( LAST_INSERT_ID(), (select pos_id FROM Baseball.Position WHERE pos_name = 'P'));

Insert Into Player(player_name, bteam_id) VALUES('A',(SELECT bteam_id FROM bTeam WHERE bteam_name = 'Washington Nationals'));
Insert Into Player(player_name, bteam_id) VALUES('B',(SELECT bteam_id FROM bTeam WHERE bteam_name = 'San Francisco Giants'));
Insert Into Player(player_name, bteam_id) VALUES('C',(SELECT bteam_id FROM bTeam WHERE bteam_name = 'Pittsburgh Pirates'));
Insert Into Player(player_name, bteam_id) VALUES('D',(SELECT bteam_id FROM bTeam WHERE bteam_name = 'New York Mets'));

DROP USER 'sql_user'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'sql_user'@'localhost' IDENTIFIED BY 'sql_user_password';
GRANT ALL PRIVILEGES ON Baseball.* TO 'sql_user'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;