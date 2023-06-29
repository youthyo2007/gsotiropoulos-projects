-- unsigned int range 0 - 4294967295


-- USERS ---------------------------------------------- 

DROP TABLE IF EXISTS `socialvideo`.`users`;
CREATE  TABLE IF NOT EXISTS `socialvideo`.`users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UUID` VARCHAR(100) NOT NULL,
  `token` MEDIUMINT(5) DEFAULT NULL ,
  `username` VARCHAR(45) DEFAULT NULL,
  `password` longtext NOT NULL ,
  `firstname` VARCHAR(45) DEFAULT NULL ,
  `middlename` VARCHAR(45) DEFAULT NULL ,
  `lastname` VARCHAR(45) DEFAULT NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `active` BOOLEAN DEFAULT 0,
  `enabled` BOOLEAN DEFAULT 0,
  `tokenexpired` BOOLEAN DEFAULT 0,
   `online` TINYINT(3) DEFAULT 0 ,
  `videoscount` int(10) unsigned  DEFAULT 0,
  `uniqueplacescount` int(10) unsigned DEFAULT 0,
  `folowercount` int(10) unsigned DEFAULT 0,
  `folowingcount` int(10) unsigned DEFAULT 0,
  `channelsfolowingcount` int(10) unsigned  DEFAULT 0,
  `communitiesfolowingcount` int(10) unsigned  DEFAULT 0,
  `tweetscount` int(10) unsigned DEFAULT 0,
  `reviewscount` int(10) unsigned DEFAULT 0,
  `likescount` int(10) unsigned DEFAULT 0,
  `datecreated` datetime  DEFAULT NOW(),
  `datemodified` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`)

 ) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `socialvideo`.`verificationtoken`;
CREATE TABLE  `socialvideo`.`verificationtoken` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
	`token` VARCHAR(100) NOT NULL,
	`dateexpired` datetime DEFAULT NOW(),
    `datecreated` datetime DEFAULT NOW(),
     -- PRIMARY KEY (`id`),
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `socialvideo`.`passwordresettoken`;
CREATE TABLE  `socialvideo`.`passwordresettoken` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
	`token` VARCHAR(100) NOT NULL,
	`dateexpired` datetime DEFAULT NOW(),
    `datecreated` datetime DEFAULT NOW(),
     -- PRIMARY KEY (`id`),
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `socialvideo`.`avatars`;
CREATE TABLE  `socialvideo`.`avatars` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
    `datablob` longblob DEFAULT NULL,
    `imagebase64` longtext DEFAULT NULL,    
	`extension` VARCHAR(45) DEFAULT NULL,
    `contenttype` VARCHAR(45) DEFAULT NULL,
	 `size` int(10) unsigned  DEFAULT 0,
	 `datecreated` datetime DEFAULT NOW(),
 	 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`avatars` ADD  INDEX `i_avatarsidx` (`id`);


-- FOLLOWERS ------------------------------------------------


DROP TABLE IF EXISTS `socialvideo`.`followers`;
CREATE TABLE  `socialvideo`.`followers` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
   `userfollowedid_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime DEFAULT NOW(),
     -- PRIMARY KEY (`id`),
	 PRIMARY KEY (`userid_fk`,`userfollowedid_fk`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`followers` ADD  INDEX `i_followersidx` (`userid_fk`);


-- MOVIES ---------------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`videos`;
CREATE TABLE  `socialvideo`.`videos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `UUID` VARCHAR(100) NOT NULL,
  `userid_fk` int(10) unsigned NOT NULL,
  `categoryid_fk` int(10) unsigned  DEFAULT NULL,
  `soundtrack_fk` int(10) unsigned  DEFAULT NULL,
  `linkedurl` varchar(100) DEFAULT '', 
   `link` BOOLEAN DEFAULT 0,
  `langid_fk` int(10) unsigned  DEFAULT NULL, 
  `title` varchar(400) NOT NULL DEFAULT '',
  `description` varchar(2000)  DEFAULT '',
  `privacy` TINYINT(3)  DEFAULT 1, -- PRIVACY SETTINGS 2 PUBLIC, 1, FOLLOWERS, 0 PRIVATE
  `latitude` varchar(100) DEFAULT '',
  `longitude` varchar(100) DEFAULT '',
  `tags` text DEFAULT NULL,
  `weather` text DEFAULT NULL,
  `thumbnail` longblob DEFAULT NULL,
  `status` TINYINT(3) DEFAULT 0 ,
  `vimeoid` int(10) unsigned DEFAULT 0 ,
  `filename` varchar(100) DEFAULT '',
  `originalfilename` varchar(100) DEFAULT '',
  `extension` varchar(10) DEFAULT '',
  `contenttype` varchar(100) DEFAULT '',
  `videosize` int(10) unsigned DEFAULT 0,
  `reviewscount` int(10) unsigned  DEFAULT 0,
  `ratingscount` int(10) unsigned  DEFAULT 0,
  `playscount` int(10) unsigned  DEFAULT 0,
  `likescount` int(10) unsigned DEFAULT 0,
  `tweetscount` int(10) unsigned DEFAULT 0,
  `duration` int(10) unsigned  DEFAULT 0, -- TODO SECS
  `footagedate` datetime  DEFAULT NOW(),
  `datecreated` datetime  DEFAULT NOW(),
  `datemodifed` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`videos` ADD FULLTEXT INDEX `i_tagsidx` (`tags`);
-- `geom` geometry DEFAULT NULL, SPATIAL KEY `i_geomidx` (`geom`)  


-- LANGUAGE -------------------------------------------------

DROP TABLE IF EXISTS `socialvideo`.`languages`;
CREATE TABLE  `socialvideo`.`languages` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `countrycode` int(10) unsigned NOT NULL,
  `countryname` varchar(50) NOT NULL,
  `language` varchar(50) NOT NULL,
  `continent` varchar(15) NOT NULL,
  `datecreated` datetime  DEFAULT NOW(),
  `datemodifed` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`languages` ADD  INDEX `i_languageidx` (`id`);



-- --------------------RATING------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`ratings`;
CREATE TABLE  `socialvideo`.`ratings` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `rating` TINYINT(3) DEFAULT 1, -- TODO 1 ---- 5
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`ratings` ADD  INDEX `i_ratingidx` (`videoid_fk`);

-- --------------------REVIEWS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`reviews`;
CREATE TABLE  `socialvideo`.`reviews` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
    `review`  varchar(500) NOT NULL, -- TODO 1 ---- 5
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`reviews` ADD  INDEX `i_reviewidx` (`videoid_fk`);


-- --------------------LIKES VIDEOS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`likesvideos`;
CREATE TABLE  `socialvideo`.`likesvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`likesvideos` ADD  INDEX `i_likesvideosidx` (`videoid_fk`);



-- --------------------PLAYS VIDEOS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`playsvideos`;
CREATE TABLE  `socialvideo`.`playsvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`playsvideos` ADD  INDEX `i_playsvideosidx` (`videoid_fk`);


-- --------------------LIKES COLLECTIONS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`likescollections`;
CREATE TABLE  `socialvideo`.`likescollections` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `collectionid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`collectionid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`likescollections` ADD  INDEX `i_likescollectionsidx` (`collectionid_fk`);



-- --------------------VIDEO SOUNDTRACKS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`soundtracks`;
CREATE TABLE  `socialvideo`.`soundtracks` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
	`title` varchar(50) NOT NULL,
    `duration` int(10) unsigned NOT NULL,  
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;




-- --------------------SHARES------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`shares`;
CREATE TABLE  `socialvideo`.`shares` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
     `socialnet_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
    
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
-- ALTER TABLE `socialvideo`.`likes` ADD  INDEX `i_likesidx` (`videoid_fk`);



-- --------------------SOCIAL NETWORK------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`socialnetworks`;
CREATE TABLE  `socialvideo`.`socialnetworks` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `shortdesc` varchar(15) NOT NULL, -- FB, 
    `description` varchar(100) NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
   PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;







-- CATEGORIES -------------------------------------------------

DROP TABLE IF EXISTS `socialvideo`.`categories`;
CREATE TABLE  `socialvideo`.`categories` (
  `id` int(10) unsigned NOT NULL,
  `parentcategory_fk` int(10) unsigned DEFAULT 0,
  `title` varchar(400) NOT NULL,
  `datecreated` datetime  DEFAULT NOW(),
  `datemodifed` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`categories` ADD  INDEX `i_categoriesidx` (`id`);

DROP TABLE IF EXISTS `socialvideo`.`categoriesvideos`;
CREATE TABLE  `socialvideo`.`categoriesvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `categoryid_fk` int(10) unsigned NOT NULL,
    `videoid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`videoid_fk`,`categoryid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`categoriesvideos` ADD  INDEX `i_categoriesvideosidx` (`categoryid_fk`);


-- CHANNELS -------------------------------------------------

DROP TABLE IF EXISTS `socialvideo`.`channels`;
CREATE TABLE  `socialvideo`.`channels` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid_fk` int(10) unsigned NOT NULL,
  `title` varchar(400) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `privacy` TINYINT(3) NOT NULL DEFAULT 1, -- PRIVACY SETTINGS 1 PUBLIC, 0 PRIVATE
  `datecreated` datetime  DEFAULT NOW(),
  `datemodifed` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`channels` ADD  INDEX `i_channelsidx` (`id`);


DROP TABLE IF EXISTS `socialvideo`.`channelsvideos`;
CREATE TABLE  `socialvideo`.`channelsvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `channelid_fk` int(10) unsigned NOT NULL,
    `videoid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`videoid_fk`,`channelid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`channelsvideos` ADD  INDEX `i_channelsvideosidx` (`channelid_fk`);

DROP TABLE IF EXISTS `socialvideo`.`channelsfollowers`;
CREATE TABLE  `socialvideo`.`channelsfollowers` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `channelid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`channelid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`channelsfollowers` ADD  INDEX `i_channelsfollowersidx` (`channelid_fk`);



-- COLLECTIONS -------------------------------------------------

DROP TABLE IF EXISTS `socialvideo`.`collections`;
CREATE TABLE  `socialvideo`.`collections` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid_fk` int(10) unsigned NOT NULL,
  `title` varchar(400) NOT NULL,
   `likescount` int(10) unsigned DEFAULT 0,
  `description` varchar(2000) NULL DEFAULT '',
  `privacy` TINYINT(3) NOT NULL DEFAULT 1, -- PRIVACY SETTINGS 1 PUBLIC, 0 PRIVATE, 2 FOLLOWERS ONLY
  `datecreated` datetime  DEFAULT NOW(),
  `datemodifed` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`collections` ADD  INDEX `i_collectionsidx` (`id`);

DROP TABLE IF EXISTS `socialvideo`.`collectionsvideos`;
CREATE TABLE  `socialvideo`.`collectionsvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `collectionid_fk` int(10) unsigned NOT NULL,
    `videoid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime DEFAULT NOW(),
  PRIMARY KEY (`videoid_fk`,`collectionid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`collectionsvideos` ADD  INDEX `i_collectionsvideosidx` (`collectionid_fk`);

-- COMMUNITIES -------------------------------------------------

DROP TABLE IF EXISTS `socialvideo`.`communities`;
CREATE TABLE  `socialvideo`.`communities` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid_fk` int(10) unsigned NOT NULL,
  `title` varchar(400) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `privacy` TINYINT(3) NOT NULL DEFAULT 1, -- PRIVACY SETTINGS 1 PUBLIC, 0 PRIVATE
  `datecreated` datetime DEFAULT NOW(),
  `datemodifed` datetime DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`communities` ADD  INDEX `i_communitiesidx` (`id`);

DROP TABLE IF EXISTS `socialvideo`.`communitiesvideos`;
CREATE TABLE  `socialvideo`.`communitiesvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `communityid_fk` int(10) unsigned NOT NULL,
    `videoid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime DEFAULT NOW(),
  PRIMARY KEY (`videoid_fk`,`communityid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`communitiesvideos` ADD  INDEX `i_communitiesvideosidx` (`communityid_fk`);



DROP TABLE IF EXISTS `socialvideo`.`communitiesfollowers`;
CREATE TABLE  `socialvideo`.`communitiesfollowers` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
	`communityid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`communityid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`communitiesfollowers` ADD  INDEX `i_communitiesfollowersidx` (`communityid_fk`);




-- -------------------------------ROLES---------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`roles`;
CREATE TABLE  `socialvideo`.`roles` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `datecreated` datetime DEFAULT NOW(),
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`roles` ADD  INDEX `i_rolesidx` (`id`);

DROP TABLE IF EXISTS `socialvideo`.`rolesusers`;
CREATE TABLE  `socialvideo`.`rolesusers` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `userid_fk` int(10) unsigned NOT NULL,
	`roleid_fk` int(10) unsigned NOT NULL,
   `datecreated` datetime DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`roleid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;



-- ------------------------- COUNT TRIGGERS -----------------------------------------
DELIMITER $$
CREATE TRIGGER userFollowChannelsCountPlus 
    AFTER INSERT ON channelsfollowers
    FOR EACH ROW BEGIN

	UPDATE users
    SET channelsfolowingcount = channelsfolowingcount + 1 WHERE id = NEW.userid_fk;
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER userFollowChannelsCountMinus 
    AFTER DELETE ON channelsfollowers
    FOR EACH ROW BEGIN
   
   -- DECLARE userid_v int(10);
   -- SELECT userid_fk into userid_v;


	UPDATE users 
    SET channelsfolowingcount = channelsfolowingcount - 1 WHERE  id = OLD.userid_fk;
END$$
DELIMITER ;
 

-- -------------------------- COUNT TRIGGERS -----------------------------------------
DELIMITER $$
CREATE TRIGGER userVideosCountPlus 
    AFTER INSERT ON videos
    FOR EACH ROW BEGIN

	UPDATE users
    SET videoscount = videoscount + 1 WHERE id = NEW.userid_fk;
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER userVideosCountMinus 
    AFTER DELETE ON videos
    FOR EACH ROW BEGIN
   

	UPDATE users 
    SET videoscount = videoscount - 1 WHERE  id = OLD.userid_fk;
END$$
DELIMITER ;



-- ---------------------------  FOLLOWERS FOLLOWING TRIGGERS COUNT UPDATES ---------------------------------
DELIMITER $$
CREATE TRIGGER userFollowUserPlus 
    AFTER INSERT ON followers
    FOR EACH ROW BEGIN

	UPDATE users SET folowingcount = folowingcount + 1 WHERE id = NEW.userid_fk;
    UPDATE users SET folowercount = folowercount + 1 WHERE id = NEW.userfollowedid_fk;
END$$
DELIMITER ;

 
DELIMITER $$
CREATE TRIGGER userUnFollowUserMinus 
    AFTER DELETE ON followers
    FOR EACH ROW BEGIN
   
	UPDATE users SET folowingcount = folowingcount - 1 WHERE id = OLD.userid_fk;
    UPDATE users SET folowercount = folowercount - 1 WHERE id = OLD.userfollowedid_fk;

END$$
DELIMITER ;










