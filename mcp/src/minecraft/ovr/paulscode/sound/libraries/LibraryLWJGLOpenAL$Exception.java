package ovr.paulscode.sound.libraries;

import paulscode.sound.SoundSystemException;

public class LibraryLWJGLOpenAL$Exception extends SoundSystemException
{
    private static final long serialVersionUID = -7502452059037798035L;
    public static final int CREATE = 101;
    public static final int INVALID_NAME = 102;
    public static final int INVALID_ENUM = 103;
    public static final int INVALID_VALUE = 104;
    public static final int INVALID_OPERATION = 105;
    public static final int OUT_OF_MEMORY = 106;
    public static final int LISTENER = 107;
    public static final int NO_AL_PITCH = 108;

    public LibraryLWJGLOpenAL$Exception(String message)
    {
        super(message);
    }

    public LibraryLWJGLOpenAL$Exception(String message, int type)
    {
        super(message, type);
    }
}
