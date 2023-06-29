
-- V.1.0.5
-- ALTER TABLE `socialvideo`.`videos` ADD FULLTEXT INDEX `i_weatheridx` (`weather`);
-- ALTER TABLE `socialvideo`.`avatars` ADD COLUMN `enabled` BOOLEAN DEFAULT 0;


-- V.1.0.6
-- ALTER TABLE `socialvideo`.`videos` 
-- CHANGE COLUMN `latitude` `latitude` DECIMAL(11,8) NULL DEFAULT 0 COMMENT '' ,
-- CHANGE COLUMN `longitude` `longitude` DECIMAL(11,8) NULL DEFAULT 0 COMMENT '' ;



-- V.1.1.0
-- ALTER TABLE `socialvideo`.`videos` ADD COLUMN `ratingssum` DECIMAL(2,1) NULL DEFAULT 0.0 COMMENT '';
-- ALTER TABLE `socialvideo`.`collections` ADD COLUMN `type` TINYINT(3) DEFAULT 0  COMMENT '';





-- V.1.1.5
-- ALTER TABLE `socialvideo`.`collections` ADD COLUMN `likescount` int(10) unsigned DEFAULT 0;

-- --------------------LIKES------------------------------------------
/*
DROP TABLE IF EXISTS `socialvideo`.`likesvideos`;
CREATE TABLE  `socialvideo`.`likesvideos` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`videoid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`likesvideos` ADD  INDEX `i_likesvideosidx` (`videoid_fk`);



-- --------------------LIKES------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`likescollections`;
CREATE TABLE  `socialvideo`.`likescollections` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `collectionid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`userid_fk`,`collectionid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`likescollections` ADD  INDEX `i_likescollectionsidx` (`collectionid_fk`);
*/


-- V.2.5
ALTER TABLE `socialvideo`.`collections` ADD COLUMN `videoscount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN `collectionscount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `youtube` BOOLEAN DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `vimeo` BOOLEAN DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `thumburl` varchar(100) DEFAULT ''; 
ALTER TABLE `socialvideo`.`users` ADD COLUMN `collectionscount` int(10) unsigned DEFAULT 0;

ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `youtubeid` varchar(100) DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `vimeoid` varchar(100) DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `videourl` varchar(100) DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `durationtxt` varchar(100) DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN    `healthstatus` TINYINT(3) DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN    `integrationstatus` TINYINT(3) DEFAULT 0;




-- V2.6
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `isfile` BOOLEAN DEFAULT 0;



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



-- V2.7
ALTER TABLE `socialvideo`.`ratings` CHANGE COLUMN `userid_fk` `userid_fk` INT(10) DEFAULT NULL COMMENT '' ;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `ratingscount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `likescount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `playscount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `favoritescount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `watchlatercount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`users` ADD COLUMN `playlistscount` int(10) unsigned DEFAULT 0;
ALTER TABLE `socialvideo`.`shares` CHANGE COLUMN `userid_fk` `userid_fk` INT(10) UNSIGNED DEFAULT NULL COMMENT '' ;
ALTER TABLE `socialvideo`.`users` ADD COLUMN  `emailvalidated` BOOLEAN DEFAULT 0;


DROP TABLE IF EXISTS `socialvideo`.`tagcloud`;
CREATE TABLE  `socialvideo`.`tagcloud` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `text` VARCHAR(100)  NOT NULL UNIQUE,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;


-- V3.1
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `rejectreason_fk` int(10) unsigned DEFAULT 0;


DROP TABLE IF EXISTS `socialvideo`.`rejectionreasons`;
CREATE TABLE  `socialvideo`.`rejectionreasons` (
    `id` int(10) unsigned NOT NULL UNIQUE,
    `descr` VARCHAR(100)  NOT NULL DEFAULT '',
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('1', 'Your video doesn\'t meet our quality requirements')
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('2', 'You have resubmitted a previously rejected video without changes')
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('3', 'Your video does not meet our maturity content guidelines')
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('4', 'Your video is not suitable due to copyright or intellectual property issues')
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('5', 'Your video has an incorrect description')
INSERT INTO `socialvideo`.`rejectionreasons` (`id`, `descr`) VALUES ('6', 'Your video has an incorrect location')
*/






-- V3.1.1
ALTER TABLE `socialvideo`.`videos` ADD COLUMN    `transferstatus` TINYINT(3) DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN    `promotesiteindex` TINYINT(3) DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN    `promotemap` TINYINT(3) DEFAULT 0;



-- V3.2.4
ALTER TABLE `socialvideo`.`collectionsvideos` ADD COLUMN    `pathsort` TINYINT(3) DEFAULT 0;
-- ALTER TABLE `socialvideo`.`collections` ADD COLUMN `pathstartvideoid` int(10) unsigned DEFAULT 0;
-- ALTER TABLE `socialvideo`.`collections` ADD COLUMN `pathendvideoid` int(10) unsigned DEFAULT 0;



-- V3.2.5
-- --------------------PLAYS COLLECTIONS------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`playscollections`;
CREATE TABLE  `socialvideo`.`playscollections` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `collectionid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`playscollections` ADD  INDEX `i_playscollectionsidx` (`collectionid_fk`);

ALTER TABLE `socialvideo`.`collections` ADD COLUMN `playscount` int(10) unsigned  DEFAULT 0;


-- V4.0.0
ALTER TABLE `socialvideo`.`users` ADD COLUMN `lastlogin` datetime;





-- V4.0.2
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `youtubechannelid` varchar(100) DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `uploaderid_fk` int(10) unsigned DEFAULT NULL;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `ownershipverified` BOOLEAN DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `ownershipverificationdate` datetime  DEFAULT NOW();
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `commentsent` BOOLEAN DEFAULT 0;
 


-- --------------------ONWERSHIP TRANFERS ------------------------------------------
DROP TABLE IF EXISTS `socialvideo`.`videoclaims`;
CREATE TABLE  `socialvideo`.`videoclaims` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `claimerid_fk` int(10) unsigned DEFAULT NULL,
    `attemptscount` TINYINT(3) DEFAULT 0,
    `status`  TINYINT(3) DEFAULT 0,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;

--v4.0.5

DROP TABLE IF EXISTS `socialvideo`.`statisticsactivityday`;
CREATE TABLE  `socialvideo`.`statisticsactivityday` (
	`dateid`  int(10) unsigned NOT NULL,
  	`videocount` int(10) unsigned DEFAULT 0,
  	`usercount` int(10) unsigned DEFAULT 0,
  	`sharecount` int(10) unsigned DEFAULT 0,
  	`ratingcount` int(10) unsigned DEFAULT 0,
  	`reviewcount` int(10) unsigned DEFAULT 0,
  	`likecount` int(10) unsigned DEFAULT 0,    
    `playcount` int(10) unsigned  DEFAULT 0,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`dateid`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






-- V4.1.5


DROP TABLE IF EXISTS `socialvideo`.`lookupvideotype`;
CREATE TABLE  `socialvideo`.`lookupvideotype` (
  `id` int(10) unsigned NOT NULL,
  `descr` varchar(400) NOT NULL,
   `active` BOOLEAN DEFAULT 0,
  `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`lookupvideotype` ADD  INDEX `i_lookupvideotypeidx` (`id`);


INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (1, 'Promotional');
INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (2, 'Area Overview');
INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (3, 'Multiple Locations');
INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (4, 'Route');
INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (5, 'Time Lapse');
INSERT INTO `socialvideo`.`lookupvideotype` (`id`, `descr`) VALUES (6, 'Action/Extreme Sports');






DROP TABLE IF EXISTS `socialvideo`.`lookupvideoshootingtype`;
CREATE TABLE  `socialvideo`.`lookupvideoshootingtype` (
  `id` int(10) unsigned NOT NULL,
  `descr` varchar(400) NOT NULL,
   `active` BOOLEAN DEFAULT 0,
  `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`lookupvideoshootingtype` ADD  INDEX `i_lookupvideoshootingtypeidx` (`id`);


INSERT INTO `socialvideo`.`lookupvideoshootingtype` (`id`, `descr`) VALUES (1, 'Aerial');
INSERT INTO `socialvideo`.`lookupvideoshootingtype` (`id`, `descr`) VALUES (2, 'Ground Level');
INSERT INTO `socialvideo`.`lookupvideoshootingtype` (`id`, `descr`) VALUES (3, 'Underwater');


DROP TABLE IF EXISTS `socialvideo`.`videotypelkp`;
CREATE TABLE  `socialvideo`.`videotypelkp` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned NOT NULL,
    `lookupid_fk` int(10) unsigned NOT NULL,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`videoid_fk`,`lookupid_fk`) 
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`videotypelkp` ADD  INDEX `i_videotypelkp` (`videoid_fk`);




ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `shootingtypeid` INT(10) NOT NULL DEFAULT 1 COMMENT '' ;



--V 4.2.6
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `terrabiscomment` BOOLEAN DEFAULT 0;



--V4.2.9
create table UserConnection (userId varchar(255) not null,
    providerId varchar(255) not null,
    providerUserId varchar(255),
    rank int not null,
    displayName varchar(255),
    profileUrl varchar(512),
    imageUrl varchar(512),
    accessToken varchar(512) not null,
    secret varchar(512),
    refreshToken varchar(512),
    expireTime bigint,
    primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);


ALTER TABLE `socialvideo`.`users` ADD COLUMN   `socialid` varchar(255) NULL DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `videotypeids` varchar(255) NULL DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `markerimgid` varchar(255) NULL DEFAULT '1';



-- V4.3.9

ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `ytvmuserid` varchar(255) NULL DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `ytvmusername` varchar(255) NULL DEFAULT '';
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  `ytvmuserlink` varchar(255) NULL DEFAULT '';

ALTER TABLE `socialvideo`.`videos` ADD COLUMN  	`modifierid_fk` int(10) unsigned NULL;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  	`approverid_fk` int(10) unsigned NULL;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  	`dateaproved` datetime  DEFAULT NOW();
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  	`modifierstatusid_fk` int(10) unsigned NULL;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN  	`datestatusmodified` datetime  DEFAULT NOW();


DROP TABLE IF EXISTS `socialvideo`.`systemproperties`;
CREATE TABLE  `socialvideo`.`systemproperties` (
    `id` int(10) unsigned NOT NULL,
    `key` varchar(255),
    `descr` varchar(255),
    `value` varchar(255),
	`modifierid_fk` int(10) unsigned NOT NULL,    
    `datemodified` datetime  DEFAULT NOW(),
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`systemproperties` ADD  INDEX `i_systemproperties` (`key`);



INSERT INTO `socialvideo`.`systemproperties`
(`id`,
`key`,
`descr`,
`value`,
`modifierid_fk`)
VALUES
(1,
'INDEX_PAGE_BACKGROUND_VIDEOURI',
'Background video URI of index page',
'vimeo:132600696',
1);



-- V4.4.4
ALTER TABLE `socialvideo`.`users` ADD COLUMN   `tstflag` BOOLEAN DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `tstflag` BOOLEAN DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `loadstatus` TINYINT(3) DEFAULT 0;
ALTER TABLE `socialvideo`.`videos` ADD COLUMN `promotionexpiredate` datetime  DEFAULT NOW();
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `rejectreasontext` varchar(255) DEFAULT ''; 





-- V4.4.5
ALTER TABLE `socialvideo`.`videos` ADD COLUMN   `markasunwanted` BOOLEAN DEFAULT 0;







-- V4.4.6
ALTER TABLE `socialvideo`.`reviews` ADD COLUMN   `status` TINYINT(3) DEFAULT 0;

DROP TABLE IF EXISTS `socialvideo`.`notifications`;
CREATE TABLE  `socialvideo`.`notifications` (
    `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `videoid_fk` int(10) unsigned DEFAULT NULL,
    `userid_fk` int(10) unsigned DEFAULT NULL,
    `actionuserid_fk` int(10) unsigned DEFAULT NULL,
     `ratingid_fk` int(10) unsigned DEFAULT NULL,
     `socialnetworkid_fk` int(10) unsigned DEFAULT NULL,
     `collectionid_fk` int(10) unsigned DEFAULT NULL,
     `activity` varchar(255) DEFAULT '',
    `status`  TINYINT(3) DEFAULT -1,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`notifications` ADD  INDEX `i_notifications` (`userid_fk`);




DROP TABLE IF EXISTS `socialvideo`.`notificationsettings`;
CREATE TABLE  `socialvideo`.`notificationsettings` (
     `id` int(10) unsigned NOT NULL UNIQUE AUTO_INCREMENT,
     `userid_fk` int(10) unsigned NOT NULL,
     `videoreview` TINYINT(3) DEFAULT 2,
     `videolike` TINYINT(3) DEFAULT 2,
     `videoshare` TINYINT(3) DEFAULT 2,
      `videorate` TINYINT(3) DEFAULT 2,
     `newfollower`TINYINT(3) DEFAULT 2,
     `videocollectionadded` TINYINT(3) DEFAULT 2,
     `performanceweekly` TINYINT(3) DEFAULT 2,
     `performancemonthly` TINYINT(3) DEFAULT 2,
     `newsletter` TINYINT(3) DEFAULT 2,
    `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2946946 DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`notificationsettings` ADD  INDEX `i_notificationsettings` (`userid_fk`);


   
DROP TABLE IF EXISTS `socialvideo`.`lookupsocialnetworks`;
CREATE TABLE  `socialvideo`.`lookupsocialnetworks` (
  `id` int(10) unsigned NOT NULL,
  `descr` varchar(400) NOT NULL,
   `active` BOOLEAN DEFAULT 0,
  `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`lookupsocialnetworks` ADD  INDEX `i_lookupsocialnetworks` (`id`);

INSERT INTO `socialvideo`.`lookupsocialnetworks` (`id`, `descr`) VALUES (1, 'Facebook');
INSERT INTO `socialvideo`.`lookupsocialnetworks` (`id`, `descr`) VALUES (2, 'Twitter');
INSERT INTO `socialvideo`.`lookupsocialnetworks` (`id`, `descr`) VALUES (3, 'Linkedin');
INSERT INTO `socialvideo`.`lookupsocialnetworks` (`id`, `descr`) VALUES (4, 'Pintereset');



DROP TABLE IF EXISTS `socialvideo`.`lookuprating`;
CREATE TABLE  `socialvideo`.`lookuprating` (
  `id` int(10) unsigned NOT NULL,
  `descr` varchar(400) NOT NULL,
   `active` BOOLEAN DEFAULT 0,
  `datecreated` datetime  DEFAULT NOW(),
  PRIMARY KEY (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `socialvideo`.`lookuprating` ADD  INDEX `i_lookuprating` (`id`);

INSERT INTO `socialvideo`.`lookuprating` (`id`, `descr`) VALUES (1, 'Hmmm..');
INSERT INTO `socialvideo`.`lookuprating` (`id`, `descr`) VALUES (2, 'Average');
INSERT INTO `socialvideo`.`lookuprating` (`id`, `descr`) VALUES (3, 'Good');
INSERT INTO `socialvideo`.`lookuprating` (`id`, `descr`) VALUES (4, 'Amazing');
INSERT INTO `socialvideo`.`lookuprating` (`id`, `descr`) VALUES (5, 'It Rocks');



