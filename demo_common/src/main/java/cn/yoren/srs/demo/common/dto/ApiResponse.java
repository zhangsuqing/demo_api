package cn.yoren.srs.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Created by miaojun on 2019/7/8.
 */

public class ApiResponse implements Serializable {

  private static final long serialVersionUID = -8706046120335187221L;

  private String resultCode;

  private String resultMessage;

  private Object data;

  public ApiResponse(String resultCode,String resultMessage,Object data){
    this.resultCode = resultCode;
    this.resultMessage = resultMessage;
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage;
  }
}

