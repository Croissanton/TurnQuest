package com.gdx.turnquest.network;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
    static public final int port = 1337;

    // This registers objects that are going to be sent over the network.
//    static public void register (EndPoint endPoint) {
//        Kryo kryo = endPoint.getKryo();
//        kryo.register(Login.class);
//        kryo.register(RegistrationRequired.class);
//        kryo.register(Register.class);
//        kryo.register(UpdateCharacter.class);
//        kryo.register(Character.class);
//
//    }
//
//    static public class Login {
//        public String name;
//    }
//
//    static public class RegistrationRequired {
//    }
//
//    static public class Register {
//        public String name;
//        public String otherStuff;
//    }
//
//    static public class UpdateCharacter {
//        public int id, x, y;
//    }
}