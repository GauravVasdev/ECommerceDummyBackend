INSERT INTO role
  VALUES (1,'admin',gen_random_uuid ()),
  (2,'user',gen_random_uuid ());

INSERT INTO urls
VALUES (1,'/admin/create/create-card'),
(2,'/admin/get/get-card'),
(3,'/admin/update/update-card'),
(4,'/admin/delete/delete-card'),
(5,'/admin/get/getAllCard');


INSERT INTO urls_role
VALUES(1,1),
(2,1),
(3,1),
(4,1),
(5,1);

Insert INTO users
VALUES(1, 'gv@gmail.com','gv123',gen_random_uuid (), 'GauravVasdev',1);