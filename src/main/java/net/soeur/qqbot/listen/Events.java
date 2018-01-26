package net.soeur.qqbot.listen;

public enum Events {

     MESSAGE_PRIVATE_FRIEND,
     MESSAGE_PRIVATE_GROUP,
     MESSAGE_PRIVATE_DISCUSS,

     MESSAGE_GROUP_NORMAL,
     MESSAGE_GROUP_ANONYMOUS,
     MESSAGE_GROUP_NOTICE,

     MESSAGE_DISCUSS,

     EVENT_GROUP_UPLOAD,

     EVENT_GROUP_ADMIN_SET,
     EVENT_GROUP_ADMIN_UNSET,

     EVENT_GROUP_DECREASE_LEAVE,
     EVENT_GROUP_DECREASE_KICK,
     EVENT_GROUP_DECREASE_KICK_ME,

     EVENT_GROUP_INCREASE_ADDROVE,
     EVENT_GROUP_INCREASE_INVITE,

     EVENT_FRIEND_ADD,

     REQUEST_FRIEND,

     REQUEST_GROUP_ADD,
     REQUEST_GROUP_INVITE,

     UNKNOWN
}