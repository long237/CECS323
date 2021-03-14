/**
 * Author:  rishi
 * Created: Mar 9, 2021
 */

--/*Table structure for table writing groups */
create table writingGroups (
    groupName varchar(10) not null,
    headWriter varchar(20) not null,
    yearFormed date not null,
    subject varchar(20) not null,
    constraint groupName_pk primary key (groupName)
);

--/*Table structure for table publisher */
create table publishers(
    publisherName varchar(20) not null,
    publisherAddress varchar(30) not null,
    publisherPhone varchar(10) not null,
    publisherEmail varchar(30) not null,
    constraint publisherName_pk primary key (publisherName)
);

--/*Table structure for table books */
create table books(
    groupName varchar(10) not null,
    bookTitle varchar(20) not null,
    publisherName varchar(20) not null,
    yearPublished date not null,
    numberPages varchar(5) not null,
    constraint bookDesc_pk primary key (bookTitle, groupName),
    constraint groupName_fk foreign key (groupName) references writingGroups (groupName),
    constraint publisherName_fk foreign key (publisherName) references publishers (publisherName)
--add in cks 
);

/*Data for Writing Groups */
insert into writinggroups (groupname, headWriter, yearFormed, subject) values
('WSB', 'John', '2013-01-06', 'Stocks'),
('Critque Circle', 'Kate', '2015-04-23', 'Grammar'),
('Fiction Writers', 'Marlow' '2008-11-01', 'Science Fiction'),
('GME', 'Apes', '2014-01-06', 'Meme Stocks')
('Big Writers', 'Ryan', '2003-07-02', 'Biographies'),
('Talentville', 'Bailey', '2019-06-18', 'Screen Writing');

/*Data for publishers */
insert into publishers (publishername, publisheraddress, publisherphone, publisheremail) values
('Reddit', 'Reddit.com', '123-456-7890', 'reddit@gmail.com'),
('Twitter', 'Twitter.com', '222-333-2323', 'Twitter@gmail.com'),
('Mimi Inc.', 'Mimi.com', '783-443-3209', 'mimiInc@yahoo.com'), 
('HarperCollins', 'Collins.com', '657-549-0121', 'harpercollins@hotmail.com'),
('Macmiller', 'Macmiller.com', '999-234-5703', 'macmiller@yahoo.com'),
('Pearson', 'Pearson.com', '453-283-6093', 'pearson@hotmail.com');

/*Data for Books*/
insert into Books(groupname, booktitle, publisherName, yearPublished, NumberPages) values
('WSB', 'To The Moon', 'Reddit', '2021-01-02', '300'),
('WSB', 'Diamond Hands', 'Reddit', '2021-02-02', '400'),
('Fiction Writers', 'White Fang', 'Macmiller', '2014-06-22', '200'),
('Talentville', 'Phantom', 'HarperCollins', '2015-09-11', '356'), 
('GME', 'Stocks', 'Twitter', '2020-07-01', '121'),
('Critque Writers', 'Grammar 101', 'Mimi Inc.', '2016-02-13', '212');

select * from Books;

