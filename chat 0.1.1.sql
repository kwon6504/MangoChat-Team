CREATE DATABASE chat DEFAULT CHARACTER SET UTF8MB4;
use chat;
DROP DATABASE chat;

CREATE TABLE login( # 유저설정
`userNo` int PRIMARY KEY AUTO_INCREMENT, # 유저 고유 넘버
`id` char(30) UNIQUE NOT NULL, # 유저 아이디
`pw` char(30) NOT NULL, # 유저 비밀번호
`userName` char(30) NOT NULL, # 유저 닉네임
`userTag` int NOT NULL DEFAULT 1, # 유저 태그번호(중복일떄 1씩 증가)
`createdAt` timestamp DEFAULT current_timestamp, # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);
select * from login;
CREATE TABLE `chatRoom`( # 채팅방 설정
`roomNo` int primary key auto_increment, # 채팅방 고유번호
`masterUserNo` int not null, # 채팅방 방장
`roomName` char(50) not null, # 채팅방 이름
`roomStat` int default 0 not null, # 0일때 오픈방 1일때 닫힌방
`userMax` int default 10 not null, # 맴버 최대 인원수
`createdAt` timestamp default current_timestamp, # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);
select * from chatRoom;
drop table chatRoom;
CREATE TABLE `chatJoin`( # 아이디 및 채팅방 링크 
`joinNo` int primary key auto_increment, # 방 고유 넘버
`userNo` int NOT NULL, # 유저 번호 저장
`roomNo` int NOT NULL, # 방 번호 저장
`createdAt` datetime not null default now(), # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);
select * from chatJoin;
drop table chatJoin;
CREATE TABLE `chatData`( # 채팅 데이터 
`chatNo` int NOT NULL AUTO_INCREMENT PRIMARY KEY, # 채팅 고유번호
`roomNo` int NOT NULL, # 방 번호 저장
`userNo` int NOT NULL, # 유저 번호 저장
`chatContent` text NOT NULL, # 채팅 내용 저장
`createdAt` timestamp default current_timestamp, # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);

select * from chatData;
drop table chatData;

CREATE TABLE `invite`( # 초대관련 테이블
`inviteNo` int PRIMARY key auto_increment, # 초대 고유번호
`userNo` int not null, # 유저 번호 저장
`receiveUserNo` int not null , # 초대받은 유저번호 저장
`roomNo` int not null default 0, # 방 번호 저장 
`inviteCase` int not null default 0, # 친구신청, 채팅방 초대 구분 [0=친구신청],[1=채팅방초대]
`createdAt` datetime not null default now(), # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);

select * from invite;
drop table invite;
SELECT COUNT(*) FROM invite WHERE (userNo = 2 AND receiveUserNo = 1 AND inviteCase = 0) or (userNo = 1 AND receiveUserNo = 2 AND inviteCase = 0);
CREATE TABLE `friendsList`( # 친구확인 테이블
`friendsListNo` int primary key auto_increment, # 친구정보 고유번호
`userNo` int, # 유저 번호 저장
`receiveUserNo` int, # 친구 번호 저장
`createdAt` datetime not null default now(), # 시간체크
`updatedAt` datetime not null default now() on update now() # 시간 업데이트 체크
);
select * from friendsList;