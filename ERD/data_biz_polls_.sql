
CREATE TABLE Answers
(
  Answers_ID int          NOT NULL COMMENT '답 고유번호',
  Answers    varchar(200) NOT NULL COMMENT '답 모음',
  PRIMARY KEY (Answers_ID)
) COMMENT '답항 모음';

CREATE TABLE Client_Survey
(
  Client_ID int          NOT NULL COMMENT '설문참여자의 고유번호',
  Name      varchar(200) NOT NULL COMMENT '설문참여자의 이름 / 아이디로 해도 될 듯?',
  PRIMARY KEY (Client_ID)
) COMMENT '고객 설문조사';

CREATE TABLE Questions
(
  Questions_ID int          NOT NULL COMMENT '문항 고유번호',
  Questions    varchar(200) NOT NULL COMMENT '이용빈도, 개선사항, 만족도, 친절도',
  PRIMARY KEY (Questions_ID)
) COMMENT '문항 모음';

CREATE TABLE Result
(
  Questions_ID int NOT NULL COMMENT '문항 고유번호',
  Answers_ID   int NOT NULL COMMENT '답 고유번호',
  Client_ID    int NOT NULL COMMENT '설문참여자의 고유번호'
) COMMENT '결과';

ALTER TABLE Result
  ADD CONSTRAINT FK_Questions_TO_Result
    FOREIGN KEY (Questions_ID)
    REFERENCES Questions (Questions_ID);

ALTER TABLE Result
  ADD CONSTRAINT FK_Answers_TO_Result
    FOREIGN KEY (Answers_ID)
    REFERENCES Answers (Answers_ID);

ALTER TABLE Result
  ADD CONSTRAINT FK_Client_Survey_TO_Result
    FOREIGN KEY (Client_ID)
    REFERENCES Client_Survey (Client_ID);
