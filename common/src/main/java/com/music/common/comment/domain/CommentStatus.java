package com.music.common.comment.domain;

import java.util.Set;

public enum CommentStatus {
    CREATED, DELETED;

    public final static Set<CommentStatus> CAN_DELETE_STATUS = Set.of(CREATED);

    }
