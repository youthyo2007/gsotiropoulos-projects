DELETE FROM `socialvideo`.`users` WHERE  `id` IN (10000,10001);
DELETE FROM `socialvideo`.`videos` WHERE  `id` IN (10000,10001,10002,10003,10004);
DELETE FROM `socialvideo`.`channels` WHERE  `id` IN (10000,10001);
DELETE FROM `socialvideo`.`verificationtoken` WHERE  `userid_fk` IN (10000);


 