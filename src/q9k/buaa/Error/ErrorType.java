package q9k.buaa.Error;

import java.util.HashMap;

public enum ErrorType {
    ILLEGALSYMBOL("a"),//符号非法
    REPEAEDNAME("b"),//名字重定义
    NOTDEFNAME("c"),//未定义的名字
    NOTPARAMNUMFIT("d"),//函数参数个数不匹配
    NOTPARAMTYPEFIT("e"),//函数参数类型不匹配
    EXTRARETURNTYPE("f"),//无返回值的函数存在不匹配的return语句
    LACKOFRETURN("g"),//有返回值的函数缺少return语句
    CHANGECONST("h"),//不能改变常量的值
    MISSINGSEMICN("i"),//缺少分号
    MISSINGRPARENT("j"),//缺少右小括号’)’
    MISSINGRBRACK("k"),//缺少右中括号’]’
    NOTPRINTFIT("l"),//printf中格式字符与表达式个数不匹配
    USINGCYCLEBC("m"),//在非循环块中使用break和continue语句
    REVERSERROR("reverse_error_code");//保留错误

    public static final HashMap<String, ErrorType> error_table = new HashMap<>();
    private String name;

    static {
        for (ErrorType errorType : ErrorType.values()){
            if(errorType.name!=null){
                error_table.put(errorType.name, errorType);
            }
        }
    }

    private ErrorType(){
        this.name = null;
    }
    private ErrorType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public static ErrorType getErrorType(String type){
        if(error_table.containsKey(type)){
            return error_table.get(type);
        }
        return REVERSERROR;
    }

}
