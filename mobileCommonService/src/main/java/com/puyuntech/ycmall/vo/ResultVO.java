package com.puyuntech.ycmall.vo;

import java.util.Map;

public class ResultVO
{
    private String code;// 响应代码
    
    private Map<?, ?> result;// 响应数据
    
    public ResultVO()
    {
        super();
    }
    
    public ResultVO(String code, Map<?, ?> result)
    {
        super();
        this.code = code;
        this.result = result;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Map<?, ?> getResult()
    {
        return result;
    }

    public void setResult(Map<?, ?> result)
    {
        this.result = result;
    }
    
    @Override
    public String toString()
    {
        return "result="+result+"\n"+ "code="+code;
    }
}
