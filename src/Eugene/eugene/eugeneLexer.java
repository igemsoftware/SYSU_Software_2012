// $ANTLR 3.1.2 C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g 2010-03-23 23:31:42

	package eugene;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class eugeneLexer extends Lexer {
    public static final int NEXTTO=34;
    public static final int NOTMORETHAN=37;
    public static final int ADDPROPS=28;
    public static final int LETTER=56;
    public static final int CONTAINS=36;
    public static final int PART=24;
    public static final int LTHAN=39;
    public static final int EQUALS=14;
    public static final int NOT=45;
    public static final int AND=43;
    public static final int LEFTP=8;
    public static final int EOF=-1;
    public static final int IF=48;
    public static final int ML_COMMENT=64;
    public static final int LEQUAL=41;
    public static final int BOOLEAN=21;
    public static final int FLEXIBLE=55;
    public static final int COMMA=17;
    public static final int NOTE=47;
    public static final int IMAGE=22;
    public static final int GEQUAL=42;
    public static final int TXT=27;
    public static final int DEVICE=25;
    public static final int UNDERS=15;
    public static final int PLUS=4;
    public static final int DIGIT=60;
    public static final int DOT=18;
    public static final int LEFTSBR=10;
    public static final int NOTCONTAINS=35;
    public static final int WITH=32;
    public static final int RIGHTSBR=11;
    public static final int LEFTCUR=12;
    public static final int NEQUAL=38;
    public static final int STRICT=54;
    public static final int RULE=26;
    public static final int RIGHTP=9;
    public static final int LINE_COMMENT=63;
    public static final int ELSE=49;
    public static final int NUMBER=58;
    public static final int PERMUTE=53;
    public static final int ON=50;
    public static final int BEFORE=30;
    public static final int NOTWITH=33;
    public static final int INT=20;
    public static final int AFTER=31;
    public static final int MULT=6;
    public static final int MINUS=5;
    public static final int ASSERT=46;
    public static final int TRUE=51;
    public static final int PRINT=29;
    public static final int NUM=19;
    public static final int REAL=59;
    public static final int WS=61;
    public static final int NEWLINE=62;
    public static final int PROPERTY=23;
    public static final int GTHAN=40;
    public static final int OR=44;
    public static final int DIV=7;
    public static final int RIGHTCUR=13;
    public static final int SEMIC=16;
    public static final int FALSE=52;
    public static final int STRING=57;

    	@Override
    	public void reportError(RecognitionException RecogEx) {
    		int line = getLine();
    		throw new IllegalArgumentException("@Error Line " + line + ": " + "Syntax error.");
    	}


    // delegates
    // delegators

    public eugeneLexer() {;} 
    public eugeneLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public eugeneLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g"; }

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:14:6: ( '+' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:14:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:15:7: ( '-' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:15:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MULT"
    public final void mMULT() throws RecognitionException {
        try {
            int _type = MULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:16:6: ( '*' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:16:8: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULT"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:17:5: ( '/' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:17:7: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "LEFTP"
    public final void mLEFTP() throws RecognitionException {
        try {
            int _type = LEFTP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:18:7: ( '(' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:18:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTP"

    // $ANTLR start "RIGHTP"
    public final void mRIGHTP() throws RecognitionException {
        try {
            int _type = RIGHTP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:19:8: ( ')' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:19:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTP"

    // $ANTLR start "LEFTSBR"
    public final void mLEFTSBR() throws RecognitionException {
        try {
            int _type = LEFTSBR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:20:9: ( '[' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:20:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTSBR"

    // $ANTLR start "RIGHTSBR"
    public final void mRIGHTSBR() throws RecognitionException {
        try {
            int _type = RIGHTSBR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:21:10: ( ']' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:21:12: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTSBR"

    // $ANTLR start "LEFTCUR"
    public final void mLEFTCUR() throws RecognitionException {
        try {
            int _type = LEFTCUR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:22:9: ( '{' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:22:11: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTCUR"

    // $ANTLR start "RIGHTCUR"
    public final void mRIGHTCUR() throws RecognitionException {
        try {
            int _type = RIGHTCUR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:23:10: ( '}' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:23:12: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTCUR"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:24:8: ( '=' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:24:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "UNDERS"
    public final void mUNDERS() throws RecognitionException {
        try {
            int _type = UNDERS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:25:8: ( '_' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:25:10: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNDERS"

    // $ANTLR start "SEMIC"
    public final void mSEMIC() throws RecognitionException {
        try {
            int _type = SEMIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:26:7: ( ';' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:26:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMIC"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:27:7: ( ',' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:27:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:28:5: ( '.' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:28:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "NUM"
    public final void mNUM() throws RecognitionException {
        try {
            int _type = NUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:29:5: ( 'num' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:29:7: 'num'
            {
            match("num"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:30:5: ( 'int' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:30:7: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:31:9: ( 'boolean' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:31:11: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "IMAGE"
    public final void mIMAGE() throws RecognitionException {
        try {
            int _type = IMAGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:32:7: ( 'Image' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:32:9: 'Image'
            {
            match("Image"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMAGE"

    // $ANTLR start "PROPERTY"
    public final void mPROPERTY() throws RecognitionException {
        try {
            int _type = PROPERTY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:33:10: ( 'Property' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:33:12: 'Property'
            {
            match("Property"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROPERTY"

    // $ANTLR start "PART"
    public final void mPART() throws RecognitionException {
        try {
            int _type = PART;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:34:6: ( 'Part' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:34:8: 'Part'
            {
            match("Part"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PART"

    // $ANTLR start "DEVICE"
    public final void mDEVICE() throws RecognitionException {
        try {
            int _type = DEVICE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:35:8: ( 'Device' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:35:10: 'Device'
            {
            match("Device"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEVICE"

    // $ANTLR start "RULE"
    public final void mRULE() throws RecognitionException {
        try {
            int _type = RULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:36:6: ( 'Rule' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:36:8: 'Rule'
            {
            match("Rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE"

    // $ANTLR start "TXT"
    public final void mTXT() throws RecognitionException {
        try {
            int _type = TXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:37:5: ( 'txt' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:37:7: 'txt'
            {
            match("txt"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TXT"

    // $ANTLR start "ADDPROPS"
    public final void mADDPROPS() throws RecognitionException {
        try {
            int _type = ADDPROPS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:38:10: ( 'addProperties' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:38:12: 'addProperties'
            {
            match("addProperties"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ADDPROPS"

    // $ANTLR start "PRINT"
    public final void mPRINT() throws RecognitionException {
        try {
            int _type = PRINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:39:7: ( 'print' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:39:9: 'print'
            {
            match("print"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRINT"

    // $ANTLR start "BEFORE"
    public final void mBEFORE() throws RecognitionException {
        try {
            int _type = BEFORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:40:8: ( 'BEFORE' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:40:10: 'BEFORE'
            {
            match("BEFORE"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BEFORE"

    // $ANTLR start "AFTER"
    public final void mAFTER() throws RecognitionException {
        try {
            int _type = AFTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:41:7: ( 'AFTER' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:41:9: 'AFTER'
            {
            match("AFTER"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AFTER"

    // $ANTLR start "WITH"
    public final void mWITH() throws RecognitionException {
        try {
            int _type = WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:42:6: ( 'WITH' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:42:8: 'WITH'
            {
            match("WITH"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WITH"

    // $ANTLR start "NOTWITH"
    public final void mNOTWITH() throws RecognitionException {
        try {
            int _type = NOTWITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:43:9: ( 'NOTWITH' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:43:11: 'NOTWITH'
            {
            match("NOTWITH"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTWITH"

    // $ANTLR start "NEXTTO"
    public final void mNEXTTO() throws RecognitionException {
        try {
            int _type = NEXTTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:44:8: ( 'NEXTTO' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:44:10: 'NEXTTO'
            {
            match("NEXTTO"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEXTTO"

    // $ANTLR start "NOTCONTAINS"
    public final void mNOTCONTAINS() throws RecognitionException {
        try {
            int _type = NOTCONTAINS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:45:13: ( 'NOTCONTAINS' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:45:15: 'NOTCONTAINS'
            {
            match("NOTCONTAINS"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTCONTAINS"

    // $ANTLR start "CONTAINS"
    public final void mCONTAINS() throws RecognitionException {
        try {
            int _type = CONTAINS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:46:10: ( 'CONTAINS' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:46:12: 'CONTAINS'
            {
            match("CONTAINS"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTAINS"

    // $ANTLR start "NOTMORETHAN"
    public final void mNOTMORETHAN() throws RecognitionException {
        try {
            int _type = NOTMORETHAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:47:13: ( 'NOTMORETHAN' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:47:15: 'NOTMORETHAN'
            {
            match("NOTMORETHAN"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTMORETHAN"

    // $ANTLR start "NEQUAL"
    public final void mNEQUAL() throws RecognitionException {
        try {
            int _type = NEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:48:8: ( '!=' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:48:10: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEQUAL"

    // $ANTLR start "LTHAN"
    public final void mLTHAN() throws RecognitionException {
        try {
            int _type = LTHAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:49:7: ( '<' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:49:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LTHAN"

    // $ANTLR start "GTHAN"
    public final void mGTHAN() throws RecognitionException {
        try {
            int _type = GTHAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:50:7: ( '>' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:50:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GTHAN"

    // $ANTLR start "LEQUAL"
    public final void mLEQUAL() throws RecognitionException {
        try {
            int _type = LEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:51:8: ( '<=' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:51:10: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEQUAL"

    // $ANTLR start "GEQUAL"
    public final void mGEQUAL() throws RecognitionException {
        try {
            int _type = GEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:52:8: ( '>=' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:52:10: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GEQUAL"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:53:5: ( 'AND' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:53:7: 'AND'
            {
            match("AND"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:54:4: ( 'OR' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:54:6: 'OR'
            {
            match("OR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:55:5: ( 'NOT' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:55:7: 'NOT'
            {
            match("NOT"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "ASSERT"
    public final void mASSERT() throws RecognitionException {
        try {
            int _type = ASSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:56:8: ( 'Assert' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:56:10: 'Assert'
            {
            match("Assert"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSERT"

    // $ANTLR start "NOTE"
    public final void mNOTE() throws RecognitionException {
        try {
            int _type = NOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:57:6: ( 'Note' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:57:8: 'Note'
            {
            match("Note"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTE"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:58:4: ( 'if' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:58:6: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:59:6: ( 'else' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:59:8: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:60:4: ( 'on' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:60:6: 'on'
            {
            match("on"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:61:6: ( 'true' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:61:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:62:7: ( 'false' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:62:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "PERMUTE"
    public final void mPERMUTE() throws RecognitionException {
        try {
            int _type = PERMUTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:63:9: ( 'permute' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:63:11: 'permute'
            {
            match("permute"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERMUTE"

    // $ANTLR start "STRICT"
    public final void mSTRICT() throws RecognitionException {
        try {
            int _type = STRICT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:64:8: ( 'strict' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:64:10: 'strict'
            {
            match("strict"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRICT"

    // $ANTLR start "FLEXIBLE"
    public final void mFLEXIBLE() throws RecognitionException {
        try {
            int _type = FLEXIBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:65:10: ( 'flexible' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:65:12: 'flexible'
            {
            match("flexible"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLEXIBLE"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2767:2: ( ( DIGIT )+ )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2767:4: ( DIGIT )+
            {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2767:4: ( DIGIT )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2767:5: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2771:2: ( NUMBER '.' NUMBER )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2771:4: NUMBER '.' NUMBER
            {
            mNUMBER(); 
            match('.'); 
            mNUMBER(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REAL"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2775:2: ( ( '\\t' | ' ' | '\\u000C' )+ )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2775:4: ( '\\t' | ' ' | '\\u000C' )+
            {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2775:4: ( '\\t' | ' ' | '\\u000C' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\t'||LA2_0=='\f'||LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)=='\f'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2779:2: ( ( '\\r' )? '\\n' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2779:4: ( '\\r' )? '\\n'
            {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2779:4: ( '\\r' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\r') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2779:4: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' ) | '//' (~ ( '\\n' | '\\r' ) )* )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:4: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' )
                    {
                    match("//"); 

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:9: (~ ( '\\n' | '\\r' ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='\u0000' && LA4_0<='\t')||(LA4_0>='\u000B' && LA4_0<='\f')||(LA4_0>='\u000E' && LA4_0<='\uFFFF')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:9: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:24: ( '\\r\\n' | '\\r' | '\\n' )
                    int alt5=3;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='\r') ) {
                        int LA5_1 = input.LA(2);

                        if ( (LA5_1=='\n') ) {
                            alt5=1;
                        }
                        else {
                            alt5=2;}
                    }
                    else if ( (LA5_0=='\n') ) {
                        alt5=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;
                    }
                    switch (alt5) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:25: '\\r\\n'
                            {
                            match("\r\n"); 


                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:34: '\\r'
                            {
                            match('\r'); 

                            }
                            break;
                        case 3 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2783:41: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    			skip();
                    		

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2787:4: '//' (~ ( '\\n' | '\\r' ) )*
                    {
                    match("//"); 

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2787:9: (~ ( '\\n' | '\\r' ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='\u0000' && LA6_0<='\t')||(LA6_0>='\u000B' && LA6_0<='\f')||(LA6_0>='\u000E' && LA6_0<='\uFFFF')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2787:9: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    			skip();
                    		

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2794:2: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2794:4: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2794:9: ( options {greedy=false; } : . )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='*') ) {
                    int LA8_1 = input.LA(2);

                    if ( (LA8_1=='/') ) {
                        alt8=2;
                    }
                    else if ( ((LA8_1>='\u0000' && LA8_1<='.')||(LA8_1>='0' && LA8_1<='\uFFFF')) ) {
                        alt8=1;
                    }


                }
                else if ( ((LA8_0>='\u0000' && LA8_0<=')')||(LA8_0>='+' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2794:36: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2798:2: ( '0' .. '9' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2798:4: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            int _type = LETTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2802:2: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2802:4: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2802:27: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')||(LA9_0>='A' && LA9_0<='Z')||LA9_0=='_'||(LA9_0>='a' && LA9_0<='z')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2806:2: ( '\"' ( options {greedy=false; } : . )* '\"' )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2806:4: '\"' ( options {greedy=false; } : . )* '\"'
            {
            match('\"'); 
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2806:8: ( options {greedy=false; } : . )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='\"') ) {
                    alt10=2;
                }
                else if ( ((LA10_0>='\u0000' && LA10_0<='!')||(LA10_0>='#' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2806:35: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    public void mTokens() throws RecognitionException {
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:8: ( PLUS | MINUS | MULT | DIV | LEFTP | RIGHTP | LEFTSBR | RIGHTSBR | LEFTCUR | RIGHTCUR | EQUALS | UNDERS | SEMIC | COMMA | DOT | NUM | INT | BOOLEAN | IMAGE | PROPERTY | PART | DEVICE | RULE | TXT | ADDPROPS | PRINT | BEFORE | AFTER | WITH | NOTWITH | NEXTTO | NOTCONTAINS | CONTAINS | NOTMORETHAN | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL | AND | OR | NOT | ASSERT | NOTE | IF | ELSE | ON | TRUE | FALSE | PERMUTE | STRICT | FLEXIBLE | NUMBER | REAL | WS | NEWLINE | LINE_COMMENT | ML_COMMENT | LETTER | STRING )
        int alt11=60;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:10: PLUS
                {
                mPLUS(); 

                }
                break;
            case 2 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:15: MINUS
                {
                mMINUS(); 

                }
                break;
            case 3 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:21: MULT
                {
                mMULT(); 

                }
                break;
            case 4 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:26: DIV
                {
                mDIV(); 

                }
                break;
            case 5 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:30: LEFTP
                {
                mLEFTP(); 

                }
                break;
            case 6 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:36: RIGHTP
                {
                mRIGHTP(); 

                }
                break;
            case 7 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:43: LEFTSBR
                {
                mLEFTSBR(); 

                }
                break;
            case 8 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:51: RIGHTSBR
                {
                mRIGHTSBR(); 

                }
                break;
            case 9 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:60: LEFTCUR
                {
                mLEFTCUR(); 

                }
                break;
            case 10 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:68: RIGHTCUR
                {
                mRIGHTCUR(); 

                }
                break;
            case 11 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:77: EQUALS
                {
                mEQUALS(); 

                }
                break;
            case 12 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:84: UNDERS
                {
                mUNDERS(); 

                }
                break;
            case 13 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:91: SEMIC
                {
                mSEMIC(); 

                }
                break;
            case 14 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:97: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 15 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:103: DOT
                {
                mDOT(); 

                }
                break;
            case 16 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:107: NUM
                {
                mNUM(); 

                }
                break;
            case 17 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:111: INT
                {
                mINT(); 

                }
                break;
            case 18 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:115: BOOLEAN
                {
                mBOOLEAN(); 

                }
                break;
            case 19 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:123: IMAGE
                {
                mIMAGE(); 

                }
                break;
            case 20 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:129: PROPERTY
                {
                mPROPERTY(); 

                }
                break;
            case 21 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:138: PART
                {
                mPART(); 

                }
                break;
            case 22 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:143: DEVICE
                {
                mDEVICE(); 

                }
                break;
            case 23 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:150: RULE
                {
                mRULE(); 

                }
                break;
            case 24 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:155: TXT
                {
                mTXT(); 

                }
                break;
            case 25 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:159: ADDPROPS
                {
                mADDPROPS(); 

                }
                break;
            case 26 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:168: PRINT
                {
                mPRINT(); 

                }
                break;
            case 27 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:174: BEFORE
                {
                mBEFORE(); 

                }
                break;
            case 28 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:181: AFTER
                {
                mAFTER(); 

                }
                break;
            case 29 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:187: WITH
                {
                mWITH(); 

                }
                break;
            case 30 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:192: NOTWITH
                {
                mNOTWITH(); 

                }
                break;
            case 31 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:200: NEXTTO
                {
                mNEXTTO(); 

                }
                break;
            case 32 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:207: NOTCONTAINS
                {
                mNOTCONTAINS(); 

                }
                break;
            case 33 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:219: CONTAINS
                {
                mCONTAINS(); 

                }
                break;
            case 34 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:228: NOTMORETHAN
                {
                mNOTMORETHAN(); 

                }
                break;
            case 35 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:240: NEQUAL
                {
                mNEQUAL(); 

                }
                break;
            case 36 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:247: LTHAN
                {
                mLTHAN(); 

                }
                break;
            case 37 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:253: GTHAN
                {
                mGTHAN(); 

                }
                break;
            case 38 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:259: LEQUAL
                {
                mLEQUAL(); 

                }
                break;
            case 39 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:266: GEQUAL
                {
                mGEQUAL(); 

                }
                break;
            case 40 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:273: AND
                {
                mAND(); 

                }
                break;
            case 41 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:277: OR
                {
                mOR(); 

                }
                break;
            case 42 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:280: NOT
                {
                mNOT(); 

                }
                break;
            case 43 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:284: ASSERT
                {
                mASSERT(); 

                }
                break;
            case 44 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:291: NOTE
                {
                mNOTE(); 

                }
                break;
            case 45 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:296: IF
                {
                mIF(); 

                }
                break;
            case 46 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:299: ELSE
                {
                mELSE(); 

                }
                break;
            case 47 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:304: ON
                {
                mON(); 

                }
                break;
            case 48 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:307: TRUE
                {
                mTRUE(); 

                }
                break;
            case 49 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:312: FALSE
                {
                mFALSE(); 

                }
                break;
            case 50 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:318: PERMUTE
                {
                mPERMUTE(); 

                }
                break;
            case 51 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:326: STRICT
                {
                mSTRICT(); 

                }
                break;
            case 52 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:333: FLEXIBLE
                {
                mFLEXIBLE(); 

                }
                break;
            case 53 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:342: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 54 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:349: REAL
                {
                mREAL(); 

                }
                break;
            case 55 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:354: WS
                {
                mWS(); 

                }
                break;
            case 56 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:357: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 57 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:365: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 58 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:378: ML_COMMENT
                {
                mML_COMMENT(); 

                }
                break;
            case 59 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:389: LETTER
                {
                mLETTER(); 

                }
                break;
            case 60 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1:396: STRING
                {
                mSTRING(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA7_eotS =
        "\2\uffff\2\5\2\uffff";
    static final String DFA7_eofS =
        "\6\uffff";
    static final String DFA7_minS =
        "\2\57\2\0\2\uffff";
    static final String DFA7_maxS =
        "\2\57\2\uffff\2\uffff";
    static final String DFA7_acceptS =
        "\4\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\2\uffff\1\1\1\0\2\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\1",
            "\1\2",
            "\12\3\1\4\2\3\1\4\ufff2\3",
            "\12\3\1\4\2\3\1\4\ufff2\3",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "2782:1: LINE_COMMENT : ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' ) | '//' (~ ( '\\n' | '\\r' ) )* );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_3 = input.LA(1);

                        s = -1;
                        if ( ((LA7_3>='\u0000' && LA7_3<='\t')||(LA7_3>='\u000B' && LA7_3<='\f')||(LA7_3>='\u000E' && LA7_3<='\uFFFF')) ) {s = 3;}

                        else if ( (LA7_3=='\n'||LA7_3=='\r') ) {s = 4;}

                        else s = 5;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_2 = input.LA(1);

                        s = -1;
                        if ( ((LA7_2>='\u0000' && LA7_2<='\t')||(LA7_2>='\u000B' && LA7_2<='\f')||(LA7_2>='\u000E' && LA7_2<='\uFFFF')) ) {s = 3;}

                        else if ( (LA7_2=='\n'||LA7_2=='\r') ) {s = 4;}

                        else s = 5;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA11_eotS =
        "\4\uffff\1\56\7\uffff\1\57\3\uffff\17\52\1\uffff\1\110\1\112\5"+
        "\52\1\121\10\uffff\2\52\1\125\24\52\4\uffff\1\152\1\52\1\154\3\52"+
        "\2\uffff\1\160\1\161\1\uffff\6\52\1\170\6\52\1\177\2\52\1\u0085"+
        "\3\52\1\uffff\1\52\1\uffff\3\52\2\uffff\3\52\1\u0090\1\52\1\u0092"+
        "\1\uffff\1\u0093\5\52\1\uffff\1\52\1\u009a\3\52\1\uffff\1\52\1\u009f"+
        "\1\52\1\u00a1\4\52\1\u00a6\1\52\1\uffff\1\52\2\uffff\1\52\1\u00aa"+
        "\2\52\1\u00ad\1\52\1\uffff\4\52\1\uffff\1\52\1\uffff\1\u00b4\3\52"+
        "\1\uffff\1\52\1\u00b9\1\52\1\uffff\1\52\1\u00bc\1\uffff\1\u00bd"+
        "\3\52\1\u00c1\1\52\1\uffff\1\52\1\u00c4\1\u00c5\1\52\1\uffff\1\52"+
        "\1\u00c8\2\uffff\1\u00c9\2\52\1\uffff\2\52\2\uffff\1\u00ce\1\52"+
        "\2\uffff\2\52\1\u00d2\1\u00d3\1\uffff\3\52\2\uffff\4\52\1\u00db"+
        "\1\u00dc\1\52\2\uffff\1\u00de\1\uffff";
    static final String DFA11_eofS =
        "\u00df\uffff";
    static final String DFA11_minS =
        "\1\11\3\uffff\1\52\7\uffff\1\60\3\uffff\1\165\1\146\1\157\1\155"+
        "\1\141\1\145\1\165\1\162\1\144\1\145\1\105\1\106\1\111\1\105\1\117"+
        "\1\uffff\2\75\1\122\1\154\1\156\1\141\1\164\1\56\10\uffff\1\155"+
        "\1\164\1\60\1\157\1\141\1\157\1\162\1\166\1\154\1\164\1\165\1\144"+
        "\1\151\1\162\1\106\1\124\1\104\1\163\2\124\1\130\1\164\1\116\4\uffff"+
        "\1\60\1\163\1\60\1\154\1\145\1\162\2\uffff\2\60\1\uffff\1\154\1"+
        "\147\1\160\1\164\1\151\1\145\1\60\1\145\1\120\1\156\1\155\1\117"+
        "\1\105\1\60\1\145\1\110\1\60\1\124\1\145\1\124\1\uffff\1\145\1\uffff"+
        "\1\163\1\170\1\151\2\uffff\3\145\1\60\1\143\1\60\1\uffff\1\60\1"+
        "\162\1\164\1\165\2\122\1\uffff\1\162\1\60\1\111\2\117\1\uffff\1"+
        "\124\1\60\1\101\1\60\1\145\1\151\1\143\1\141\1\60\1\162\1\uffff"+
        "\1\145\2\uffff\1\157\1\60\1\164\1\105\1\60\1\164\1\uffff\1\124\1"+
        "\116\1\122\1\117\1\uffff\1\111\1\uffff\1\60\1\142\1\164\1\156\1"+
        "\uffff\1\164\1\60\1\160\1\uffff\1\145\1\60\1\uffff\1\60\1\110\1"+
        "\124\1\105\1\60\1\116\1\uffff\1\154\2\60\1\171\1\uffff\1\145\1\60"+
        "\2\uffff\1\60\1\101\1\124\1\uffff\1\123\1\145\2\uffff\1\60\1\162"+
        "\2\uffff\1\111\1\110\2\60\1\uffff\1\164\1\116\1\101\2\uffff\1\151"+
        "\1\123\1\116\1\145\2\60\1\163\2\uffff\1\60\1\uffff";
    static final String DFA11_maxS =
        "\1\175\3\uffff\1\57\7\uffff\1\172\3\uffff\1\165\1\156\1\157\1\155"+
        "\1\162\1\145\1\165\1\170\1\144\1\162\1\105\1\163\1\111\1\157\1\117"+
        "\1\uffff\2\75\1\122\1\154\1\156\1\154\1\164\1\71\10\uffff\1\155"+
        "\1\164\1\172\1\157\1\141\1\157\1\162\1\166\1\154\1\164\1\165\1\144"+
        "\1\151\1\162\1\106\1\124\1\104\1\163\2\124\1\130\1\164\1\116\4\uffff"+
        "\1\172\1\163\1\172\1\154\1\145\1\162\2\uffff\2\172\1\uffff\1\154"+
        "\1\147\1\160\1\164\1\151\1\145\1\172\1\145\1\120\1\156\1\155\1\117"+
        "\1\105\1\172\1\145\1\110\1\172\1\124\1\145\1\124\1\uffff\1\145\1"+
        "\uffff\1\163\1\170\1\151\2\uffff\3\145\1\172\1\143\1\172\1\uffff"+
        "\1\172\1\162\1\164\1\165\2\122\1\uffff\1\162\1\172\1\111\2\117\1"+
        "\uffff\1\124\1\172\1\101\1\172\1\145\1\151\1\143\1\141\1\172\1\162"+
        "\1\uffff\1\145\2\uffff\1\157\1\172\1\164\1\105\1\172\1\164\1\uffff"+
        "\1\124\1\116\1\122\1\117\1\uffff\1\111\1\uffff\1\172\1\142\1\164"+
        "\1\156\1\uffff\1\164\1\172\1\160\1\uffff\1\145\1\172\1\uffff\1\172"+
        "\1\110\1\124\1\105\1\172\1\116\1\uffff\1\154\2\172\1\171\1\uffff"+
        "\1\145\1\172\2\uffff\1\172\1\101\1\124\1\uffff\1\123\1\145\2\uffff"+
        "\1\172\1\162\2\uffff\1\111\1\110\2\172\1\uffff\1\164\1\116\1\101"+
        "\2\uffff\1\151\1\123\1\116\1\145\2\172\1\163\2\uffff\1\172\1\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\uffff\1\15\1\16\1\17\17\uffff\1\43\10\uffff\1\67\1\70\1\73\1\74"+
        "\1\71\1\72\1\4\1\14\27\uffff\1\46\1\44\1\47\1\45\6\uffff\1\65\1"+
        "\66\2\uffff\1\55\24\uffff\1\51\1\uffff\1\57\3\uffff\1\20\1\21\6"+
        "\uffff\1\30\6\uffff\1\50\5\uffff\1\52\12\uffff\1\25\1\uffff\1\27"+
        "\1\60\6\uffff\1\35\4\uffff\1\54\1\uffff\1\56\4\uffff\1\23\3\uffff"+
        "\1\32\2\uffff\1\34\6\uffff\1\61\4\uffff\1\26\2\uffff\1\33\1\53\3"+
        "\uffff\1\37\2\uffff\1\63\1\22\2\uffff\1\62\1\36\4\uffff\1\24\3\uffff"+
        "\1\41\1\64\7\uffff\1\40\1\42\1\uffff\1\31";
    static final String DFA11_specialS =
        "\u00df\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\50\1\51\1\uffff\1\50\1\51\22\uffff\1\50\1\37\1\53\5\uffff"+
            "\1\5\1\6\1\3\1\1\1\16\1\2\1\17\1\4\12\47\1\uffff\1\15\1\40\1"+
            "\13\1\41\2\uffff\1\33\1\32\1\36\1\25\4\52\1\23\4\52\1\35\1\42"+
            "\1\24\1\52\1\26\4\52\1\34\3\52\1\7\1\uffff\1\10\1\uffff\1\14"+
            "\1\uffff\1\30\1\22\2\52\1\43\1\45\2\52\1\21\4\52\1\20\1\44\1"+
            "\31\2\52\1\46\1\27\6\52\1\11\1\uffff\1\12",
            "",
            "",
            "",
            "\1\55\4\uffff\1\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "",
            "",
            "\1\60",
            "\1\62\7\uffff\1\61",
            "\1\63",
            "\1\64",
            "\1\66\20\uffff\1\65",
            "\1\67",
            "\1\70",
            "\1\72\5\uffff\1\71",
            "\1\73",
            "\1\75\14\uffff\1\74",
            "\1\76",
            "\1\77\7\uffff\1\100\44\uffff\1\101",
            "\1\102",
            "\1\104\11\uffff\1\103\37\uffff\1\105",
            "\1\106",
            "",
            "\1\107",
            "\1\111",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116\12\uffff\1\117",
            "\1\120",
            "\1\122\1\uffff\12\47",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\123",
            "\1\124",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "",
            "",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\153",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\155",
            "\1\156",
            "\1\157",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u0080",
            "\1\u0081",
            "\12\52\7\uffff\2\52\1\u0083\11\52\1\u0084\11\52\1\u0082\3"+
            "\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "",
            "\1\u0089",
            "",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "",
            "",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u0091",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "",
            "\1\u0099",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "",
            "\1\u009e",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00a0",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00a7",
            "",
            "\1\u00a8",
            "",
            "",
            "\1\u00a9",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00ab",
            "\1\u00ac",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00ae",
            "",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "",
            "\1\u00b3",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "",
            "\1\u00b8",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00ba",
            "",
            "\1\u00bb",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00c2",
            "",
            "\1\u00c3",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00c6",
            "",
            "\1\u00c7",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00ca",
            "\1\u00cb",
            "",
            "\1\u00cc",
            "\1\u00cd",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00cf",
            "",
            "",
            "\1\u00d0",
            "\1\u00d1",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "",
            "",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            "\1\u00dd",
            "",
            "",
            "\12\52\7\uffff\32\52\4\uffff\1\52\1\uffff\32\52",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( PLUS | MINUS | MULT | DIV | LEFTP | RIGHTP | LEFTSBR | RIGHTSBR | LEFTCUR | RIGHTCUR | EQUALS | UNDERS | SEMIC | COMMA | DOT | NUM | INT | BOOLEAN | IMAGE | PROPERTY | PART | DEVICE | RULE | TXT | ADDPROPS | PRINT | BEFORE | AFTER | WITH | NOTWITH | NEXTTO | NOTCONTAINS | CONTAINS | NOTMORETHAN | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL | AND | OR | NOT | ASSERT | NOTE | IF | ELSE | ON | TRUE | FALSE | PERMUTE | STRICT | FLEXIBLE | NUMBER | REAL | WS | NEWLINE | LINE_COMMENT | ML_COMMENT | LETTER | STRING );";
        }
    }
 

}