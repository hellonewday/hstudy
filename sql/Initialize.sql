CREATE TABLE Account (
	id int auto_increment not null,
    username varchar(255),
    password varchar(255),
    primary key (id)
);

CREATE TABLE Student(
	sid int auto_increment not null,
    account int,
    email varchar(255),
    avatarUrl varchar(255),
    created_at timestamp default now(),
    dob date,
    primary key (sid),
    foreign key (account) references Account(id) on delete cascade
);

CREATE TABLE CourseProvider(
	id int auto_increment not null,
    providerName varchar(255) not null,
    location varchar(255) not null,
    imageUrl varchar(255) not null,
    description varchar(255),
    primary key (id)
);

CREATE TABLE Author (
	id int auto_increment not null,
    provider int,
    description varchar(255),
    fullname varchar(255),
    gender enum("Male","Female"),
    primary key (id),
    foreign key (provider) references CourseProvider(id) on delete cascade
);

CREATE TABLE Course (
	id int auto_increment not null,
    courseName varchar(255),
    level enum("Beginner","Intermediate","Advanced"),
    author int,
    description varchar(255),
    courseImage varchar(255),
    primary key (id),
    foreign key (author) references Author(id) on delete cascade
);

CREATE TABLE StudentCourse(
	sid int not null,
    cid int not null,
    type enum("Standard","Paid") not null,
    foreign key (sid) references Student(sid) on delete cascade,
    foreign key (cid) references Course(id) on delete cascade
);

CREATE TABLE VideoCourse(
	id int auto_increment not null,
    videoName varchar(255),
    videoUrl varchar(255),
    course int,
    primary key (id),
    foreign key (course) references Course(id) on delete cascade
); 

CREATE TABLE StudentVideo(
	sid int not null,
    vid int not null,
    status enum("Ongoing","Done"),
	foreign key (sid) references Student(sid) on delete cascade,
    foreign key (vid) references VideoCourse(id) on delete cascade
);
