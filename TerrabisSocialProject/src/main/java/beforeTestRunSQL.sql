INSERT INTO `socialvideo`.`users` (`id`, `token`, `username`, `password`, `firstname`, `middlename`, `lastname`, `email`, `active`, `online`) VALUES ('10000', '1', 'youth', '1234', 'testfirst', 'n', 'testlast', 'test1@gmail.com', '1', '1');
INSERT INTO `socialvideo`.`users` (`id`, `token`, `username`, `password`, `firstname`, `middlename`, `lastname`, `email`, `active`, `online`) VALUES ('10001', '1', 'fatboy', '1234', 'testfirst', 'n', 'testlast', 'test2@gmail.com', '1', '1');
INSERT INTO `socialvideo`.`verificationtoken` (`userid_fk`, `token`,`dateexpired`) VALUES ('10000', 'ABCD', '2015-09-17 08:21:53');

INSERT INTO `socialvideo`.`videos` (`id`, `userid_fk`, `title`, `description`, `rating`, `latitude`, `longitude`, `tags`, `playscount`, `likescount`, `duration`,`datecreated`) VALUES ('10000', '10000', 'Drone Documentary Corinth', 'A Drone Documentary on Corinth', '3', '37.938637', '22.932238', 'drone,documentary,corinth,greece', '10', '10', '720000','2014-09-08 08:21:53');
INSERT INTO `socialvideo`.`videos` (`id`, `userid_fk`, `title`, `description`, `rating`, `latitude`, `longitude`, `tags`, `playscount`, `likescount`, `duration`,`datecreated`) VALUES ('10001', '10000', 'Drone Documentary Athens', 'A Drone Documentary on Athens, Center', '7', '37.983917', '23.729360', 'drone,documentary,athens,greece', '20', '20', '1440000','2015-09-07 08:21:53');
INSERT INTO `socialvideo`.`videos` (`id`, `userid_fk`, `title`, `description`, `rating`, `latitude`, `longitude`, `tags`, `playscount`, `likescount`, `duration`,`datecreated`) VALUES ('10002', '10001', 'Drone Documentary Athens', 'A Drone Documentary on Athens, γλυφάδα', '10', '37.870136', '23.752294', 'drone,documentary,athens,greece,γλυφάδα', '6', '15', '4320000','2015-08-17 08:21:53');
INSERT INTO `socialvideo`.`videos` (`id`, `userid_fk`, `title`, `description`, `rating`, `latitude`, `longitude`, `tags`, `playscount`, `likescount`, `duration`,`datecreated`) VALUES ('10003', '10001', 'Drone Documentary Athens', 'A Drone Documentary on Paros, Greece', '9', '37.870136', '23.752294', 'drone,documentary,athens,greece,πάρος', '6', '15', '4320000','2015-08-10 08:21:53');
INSERT INTO `socialvideo`.`videos` (`id`, `userid_fk`, `title`, `description`, `rating`, `latitude`, `longitude`, `tags`, `playscount`, `likescount`, `duration`,`datecreated`) VALUES ('10004', '10001', 'Drone Documentary Rome Today', 'A Drone Documentary on Rome, Italy', '9', '37.870136', '23.752294', 'drone,documentary,rome,italy,πάρος', '6', '15', '4320000','2015-09-14 01:20:53');

INSERT INTO `socialvideo`.`channels` (`id`, `userid_fk`, `title`, `description`, `privacy`) VALUES ('10000', '10000', 'Documentaries', 'Documentaries About Drones', '1');
INSERT INTO `socialvideo`.`channels` (`id`, `userid_fk`, `title`, `description`, `privacy`) VALUES ('10001', '10000', 'World Capitals', 'Documentaries World Capitals', '1');








