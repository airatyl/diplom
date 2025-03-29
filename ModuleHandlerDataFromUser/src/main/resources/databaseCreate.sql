SET search_path TO public;
create table sensorModels
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45) NOT NULL ,
    Description character varying(100)
);

create table sensorTypes
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table sensors
(
    id          SERIAL Primary key,
    Place       character varying(45) NOT NULL,
    SensorModel INTEGER NOT NULL,
    SensorType  INTEGER NOT NULL,
    FOREIGN KEY (SensorModel) REFERENCES sensorModels (id),
    FOREIGN KEY (SensorType) REFERENCES sensorTypes (id)
);

create table sensorValue
(
    id            SERIAL PRIMARY KEY,
    value         real NOT NULL,
    recordingTime TIMESTAMP NOT NULL,
    sensor      INTEGER NOT NULL,
    FOREIGN KEY (sensor) REFERENCES sensors (id)
);

create table moldingStages
(
    id          INTEGER PRIMARY KEY,
    name        character varying(45) NOT NULL,
    description character varying(100)
);

create table paramOnEachStage
(
     controlParam      character varying(45) NOT NULL,
    unitOfMeasurement character varying(10) NOT NULL,
    minValue          real NOT NULL,
    maxValue          real NOT NULL,

    needControl       BOOLEAN NOT NULL,
    sensor            INTEGER,
    moldingStage      INTEGER,
    PRIMARY KEY (sensor,moldingStage),
    FOREIGN KEY (sensor) REFERENCES sensors (id),
    FOREIGN KEY (moldingStage) REFERENCES moldingStages (id)
);

insert into sensormodels (name, description)
VALUES ('dht12', 'описание для dht12');

insert into sensortypes (name, description)
VALUES ('датчик температуры', 'описание для датчика температуры');

insert into sensors (place, sensormodel, sensortype)
VALUES ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1);

insert into moldingStages (id,name, description)
values (1,'Раскрытие пресс-формы и смазка','описание для первого этапа'),
       (2,'Cмыкание пресс-формы и ее нагрев','описание для первого этапа'),
       (3,'Заливка металла в камеру прессования','описание для первого этапа'),
       (4,'Прессование','описание для первого этапа'),
       (5,'Охлаждение','описание для первого этапа'),
       (6,'Извлечение изделия','описание для первого этапа');

insert into paramOnEachStage (controlParam, unitOfMeasurement, minValue, maxValue, needControl, sensor, moldingStage)
VALUES ('Температура','°C',0,0,FALSE,1,1),
       ('Температура','°C',0,0,FALSE,1,2),
       ('Температура','°C',0,0,FALSE,1,3),
       ('Температура','°C',75,85,TRUE,1,4),
       ('Температура','°C',0,0,FALSE,1,5),
       ('Температура','°C',0,0,FALSE,2,1),
       ('Температура','°C',0,0,FALSE,2,2),
       ('Температура','°C',0,0,FALSE,2,3),
       ('Температура','°C',0,0,FALSE,2,4),
       ('Температура','°C',14,16,TRUE,2,5),
       ('Температура','°C',0,0,FALSE,3,1),
       ('Температура','°C',0,0,FALSE,3,2),
       ('Температура','°C',0,0,FALSE,3,3),
       ('Температура','°C',90,100,TRUE,3,4),
       ('Температура','°C',0,0,FALSE,3,5),
       ('Температура','°C',0,0,FALSE,4,1),
       ('Температура','°C',0,0,FALSE,4,2),
       ('Температура','°C',0,0,FALSE,4,3),
       ('Температура','°C',23,27,TRUE,4,4),
       ('Температура','°C',0,0,FALSE,4,5),
       ('Температура','°C',0,0,FALSE,5,1),
       ('Температура','°C',240,260,TRUE,5,2),
       ('Температура','°C',240,260,TRUE,5,3),
       ('Температура','°C',0,0,FALSE,5,4),
       ('Температура','°C',0,0,FALSE,5,5);



/*End first database*/
SET search_path TO kurs_sch;
create table departments
(
    id          SERIAL PRIMARY KEY,
    name        varchar(45) NOT NULL,
    Description character varying(100)
);

create table posts
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table users
(
    id         SERIAL PRIMARY KEY,
    firstName  character varying(30) NOT NULL,
    lastName   character varying(30) NOT NULL,
    post       INTEGER NOT NULL,
    department INTEGER NOT NULL,
    FOREIGN KEY (post) REFERENCES posts (id),
    FOREIGN KEY (department) REFERENCES departments (id)
);

create table operations
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table if not exists operationHistory
(
    id        SERIAL PRIMARY KEY,
    data      character varying(100) NOT NULL,
    operation INTEGER NOT NULL,
    userId    INTEGER NOT NULL,
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (operation) REFERENCES operations (id)
);
insert into departments (name, description)
values ('Отдел ИТ', 'описание отдела ИТ'),
       ('Инженерный отдел', 'описание инженерного отдела'),
       ('Бухгалтерия', 'описание бухгалтерии');
insert into posts (name, description)
values ('Оператор станка', 'описание оператора станка'),
       ('Главный инженер', 'описание главного инженера'),
       ('Бухгалтер', 'описание бухгалтера');
insert into users (firstname, lastname, post, department)
VALUES ('Иван', 'Иванов', 2, 1);

insert into operations (name, description)
VALUES ('просмотр', 'описание для просмотра');

