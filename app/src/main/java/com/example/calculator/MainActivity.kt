package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.calculator.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import org.mozilla.javascript.Context
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var viewBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        handleOnclick()
        setContentView(viewBinding.root)
    }
    private fun handleOnclick(){
        viewBinding.btn0.setOnClickListener(this)
        viewBinding.btn1.setOnClickListener(this)
        viewBinding.btn2.setOnClickListener(this)
        viewBinding.btn3.setOnClickListener(this)
        viewBinding.btn4.setOnClickListener(this)
        viewBinding.btn5.setOnClickListener(this)
        viewBinding.btn6.setOnClickListener(this)
        viewBinding.btn7.setOnClickListener(this)
        viewBinding.btn8.setOnClickListener(this)
        viewBinding.btn9.setOnClickListener(this)
        viewBinding.btnAc.setOnClickListener(this)
        viewBinding.delete.setOnClickListener(this)
        viewBinding.btnPlus.setOnClickListener(this)
        viewBinding.btnMinus.setOnClickListener(this)
        viewBinding.btnMultiple.setOnClickListener(this)
        viewBinding.btnDiv.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        val button = p0 as MaterialButton
        val buttonTextValue = button.text.toString()
        var inputText = viewBinding.inputValue.text.toString()
        when (buttonTextValue) {
            "AC" -> {
                inputText = ""
                viewBinding.outputValue.text = "0"
            }
            "D" -> {
                if(inputText.length > 1){
                inputText = inputText.substring(0, inputText.length - 1)
                viewBinding.inputValue.text = inputText
                }
            }
            else -> {
                inputText += buttonTextValue
            }
        }
        viewBinding.inputValue.text = inputText
        if(inputText != ""){
            val finalResult = getResult(inputText)
            if(finalResult != "Error"){
                viewBinding.outputValue.text = finalResult
            }
        }
    }
    private fun getResult(data : String) : String{ //Xử lý tính toán dựa trên String cho trước, sử dụng thư viện từ javaScript, nếu kết quả hợp lệ, sẽ trả về giá trị phù hợp, còn nếu string là 1 chuỗi không tính được thì sẽ là error
        return try{
            val context = Context.enter()
            context.optimizationLevel = -1
            val scriptable = context.initStandardObjects()
            var result = context.evaluateString(scriptable, data, "Javascript", 1,null).toString()
            if(result.endsWith(".0")){
                result = result.replace(".0", "")
            }else if (result.contains(".") && !result.endsWith(".0") ){
                val floatValue = result.toFloat()
                val formattedBmi = String.format(Locale.getDefault(), "%.2f", floatValue)
                result = formattedBmi
            }
            result
        }catch (e : java.lang.Exception){
            "Error"
        }
    }
}