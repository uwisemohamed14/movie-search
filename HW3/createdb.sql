CREATE TABLE movies (
    mid VARCHAR2(20),
    title VARCHAR2(200) NOT NULL,
    year INTEGER NOT NULL,
    all_critics_rating NUMBER(3, 1),
    all_critics_num INTEGER,
    top_critics_rating NUMBER(3, 1),
    top_critics_num INTEGER,
    audience_rating NUMBER(3, 1),
    audience_num INTEGER,
    PRIMARY KEY(mid)
);

CREATE TABLE movie_genres (
    mid VARCHAR2(20) NOT NULL,
    genre VARCHAR2(30) NOT NULL,
    PRIMARY KEY(mid, genre),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);

CREATE TABLE movie_countries (
    mid VARCHAR2(20) NOT NULL,
    country VARCHAR2(60),
    PRIMARY KEY(mid),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);

CREATE TABLE movie_locations (
    mid VARCHAR2(20) NOT NULL,
    country VARCHAR2(60) NOT NULL,
    PRIMARY KEY (mid, country),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);

CREATE TABLE tags (
    tid VARCHAR2(20) NOT NULL,
    tagvalue VARCHAR2(100) NOT NULL,
    PRIMARY KEY(tid)
);

CREATE TABLE movie_tags (
    mid VARCHAR2(20) NOT NULL,
    tid VARCHAR2(20) NOT NULL,
    tagweight INTEGER,
    PRIMARY KEY(mid, tid),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE,
    FOREIGN KEY (tid) REFERENCES tags(tid) ON DELETE CASCADE
);

CREATE TABLE movie_actors (
    mid VARCHAR2(20) NOT NULL,
    aid VARCHAR2(20) NOT NULL,
    aname VARCHAR2(20) NOT NULL,
    aranking INTEGER,
    PRIMARY KEY(mid, aid),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);

CREATE TABLE movie_directors (
    mid VARCHAR2(20) NOT NULL,
    did VARCHAR2(20) NOT NULL,
    dname VARCHAR2(20) NOT NULL,
    PRIMARY KEY(mid, did),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);


CREATE TABLE user_ratedmovies (
    userid VARCHAR2(20) NOT NULL,
    mid VARCHAR2(20) NOT NULL,
    rating NUMBER(3,1),
    PRIMARY KEY(userid, mid),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE
);


CREATE TABLE user_taggedmovies (
    userid VARCHAR2(60) NOT NULL,
    mid VARCHAR2(20) NOT NULL,
    tid VARCHAR2(20) NOT NULL,
    PRIMARY KEY(userid, mid, tid),
    FOREIGN KEY (mid) REFERENCES movies(mid) ON DELETE CASCADE,
    FOREIGN KEY (tid) REFERENCES tags(tid) ON DELETE CASCADE
);