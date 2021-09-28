package com.example.mickaelly.ada_ava.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mickaelly.ada_ava.AdaApplication;
import com.example.mickaelly.ada_ava.R;
import com.example.mickaelly.ada_ava.model.User;
import com.example.mickaelly.ada_ava.service.DBCommandsUser;
import com.example.mickaelly.ada_ava.util.SnackBarManager;

import java.util.ArrayList;
import java.util.List;

public class RegisterUserActivity extends AppCompatActivity {

    private TextView mTxvName;
    private EditText mEdtNameRegister;
    private EditText mEdtEmailRegister;
    private TextView mTxvPassword;
    private EditText mEdtPasswordRegister;
    private EditText mEdtPasswordConfirmRegister;
    private EditText mEdtTypeUser;
    private TextView mTxvConfirmPassword;
    private TextView mTxvTypeUser;
    private Button mBtnRegisterUser;
    private Spinner mSpnTypeUser;
    private Toolbar toolbar;
    private boolean isNewPassword;
    private SnackBarManager snackBarManager;
    private LinearLayout mRoot;
    private DBCommandsUser commandsUser;
    private View loading;
    private User user;
    private Button mBtnDeleteUser;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getExtra();
        initUI();
        configureToolbar();
        configureEvent();
        configureLayout();
        snackBarManager = new SnackBarManager(this);
        commandsUser = new DBCommandsUser(this);
    }

    private void configureLayout() {

        toolbar.setTitle(R.string.title_profile);
        user = AdaApplication.getInstance().getUserSession();
        List<String> types = new ArrayList<>();
        types.add("Aluno");
        types.add("Professor");
        types.add("Tutor");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        mSpnTypeUser.setAdapter(adapter);

        if(user != null){

            mEdtNameRegister.setText(user.getName());
            mEdtEmailRegister.setText(user.getEmail());
            for(String s : types){
                if(s.equalsIgnoreCase(AdaApplication.getInstance().getUserSession().getTypeUser())){
                    mSpnTypeUser.setSelection(adapter.getPosition(s));
                }
            }
            mSpnTypeUser.setEnabled(false);
            mBtnDeleteUser.setVisibility(View.VISIBLE);

            mBtnRegisterUser.setText(R.string.label_btn_update);

            setVisibility(View.GONE);

        } else if (isNewPassword){

            toolbar.setTitle(R.string.title_new_password_restore);
            mTxvName.setVisibility(View.GONE);
            mEdtNameRegister.setVisibility(View.GONE);
            mTxvTypeUser.setVisibility(View.GONE);
            mSpnTypeUser.setVisibility(View.GONE);
            mBtnDeleteUser.setVisibility(View.GONE);

            mBtnRegisterUser.setText(R.string.label_new_password);

        }else{
            toolbar.setTitle(R.string.title_toobar_register_user);
            setVisibility(View.VISIBLE);
            mBtnRegisterUser.setText(R.string.label_btn_register);
            mBtnDeleteUser.setVisibility(View.GONE);
        }
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(mEdtNameRegister.getWindowToken(), 0);
    }

    private void setVisibility(int visibility) {
        mTxvPassword.setVisibility(visibility);
        mEdtPasswordRegister.setVisibility(visibility);
        mTxvConfirmPassword.setVisibility(visibility);
        mEdtPasswordConfirmRegister.setVisibility(visibility);
    }

    private void getExtra() {
        isNewPassword = getIntent().getExtras().getBoolean("NEW_PASSWORD");
    }

    private void initUI() {
        mTxvName = (TextView) findViewById(R.id.txv_name);
        mEdtNameRegister = (EditText) findViewById(R.id.edt_name_register);
        mEdtEmailRegister = (EditText) findViewById(R.id.edt_email_register);
        mTxvPassword = (TextView) findViewById(R.id.txv_password);
        mEdtPasswordRegister = (EditText) findViewById(R.id.edt_password_register);
        mTxvConfirmPassword = (TextView) findViewById(R.id.txv_confirm_password);
        mEdtPasswordConfirmRegister = (EditText) findViewById(R.id.edt_password_confirm_register);
        mTxvTypeUser = (TextView) findViewById(R.id.txv_type_user);
        mSpnTypeUser = (Spinner) findViewById(R.id.spn_type_user);
        mBtnRegisterUser = (Button) findViewById(R.id.btn_register_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRoot = (LinearLayout) findViewById(R.id.register_user_root);
        loading = (View) findViewById(R.id.content_loading);
        mBtnDeleteUser = (Button) findViewById(R.id.btn_delete_user);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureEvent() {
        mBtnRegisterUser.setOnClickListener(sendDates());
        mBtnDeleteUser.setOnClickListener(deleteUser());
    }

    private View.OnClickListener deleteUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                loading.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUserActivity.this);
                builder.setTitle("Atenção!");
                builder.setMessage("Tem certeza que deseja excluir seu usuário?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        long result = commandsUser.delete(user);
                        if(result > 0){
                            snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_delete_user), Snackbar.LENGTH_SHORT, sucessRegister());
                        }
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        alerta.dismiss();
                        loading.setVisibility(View.GONE);
                    }
                });

                alerta = builder.create();
                alerta.show();
            }
        };
    }

    private View.OnClickListener sendDates() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                hideKeyboard();
                if(isNewPassword){
                    if(validateFieldsNewPassword()){
                        long result = commandsUser.changePassword(mEdtEmailRegister.getText().toString(), mEdtPasswordRegister.getText().toString());
                        if(result > 0){
                            snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_new_password), Snackbar.LENGTH_SHORT, sucessRegister());
                        }else if (result == 0){
                            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_user_not_exist), Snackbar.LENGTH_SHORT, errorRegister());
                        }else{
                            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_new_password), Snackbar.LENGTH_SHORT, errorRegister());
                        }
                    }
                }else{
                    if(validateFields()){
                        long result = 0;
                        if(user.getId() != 0){
                            result = commandsUser.update(user);
                        }else{
                            result = commandsUser.insert(user);
                        }
                        if(result > 0){
                            snackBarManager.showSuccessSnackBar(mRoot, getString(R.string.success_register_user), Snackbar.LENGTH_SHORT, sucessRegister());
                        }else if (result == 0){
                            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_user_exist), Snackbar.LENGTH_SHORT, errorRegister());
                        }else{
                            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_register_user), Snackbar.LENGTH_SHORT, errorRegister());
                        }
                    }
                }
            }
        };
    }

    public Snackbar.Callback sucessRegister(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                finish();
                loading.setVisibility(View.INVISIBLE);
            }
        };
    }

    public Snackbar.Callback errorRegister(){
        return new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                loading.setVisibility(View.GONE);
            }
        };
    }

    private boolean validateFieldsNewPassword(){
        if (mEdtEmailRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_email), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }else if(mEdtPasswordRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_password), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }else if(mEdtPasswordConfirmRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_confirm_password_null), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }else if(!mEdtPasswordRegister.getText().toString().equals(mEdtPasswordConfirmRegister.getText().toString())){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_confirm_password), Snackbar.LENGTH_SHORT, errorRegister());
            return false;
        }
        return true;
    }

    private boolean validateFields() {
        if (mEdtNameRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_name), Snackbar.LENGTH_LONG, null);
            return false;
        }else if(mEdtEmailRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_email), Snackbar.LENGTH_LONG, null);
            return false;
        }else if(mEdtPasswordRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_password), Snackbar.LENGTH_LONG, null);
            return false;
        }else if(mEdtPasswordConfirmRegister.getText().toString().equals("")){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_confirm_password_null), Snackbar.LENGTH_LONG, null);
            return false;
        }else if(!mEdtPasswordRegister.getText().toString().equals(mEdtPasswordConfirmRegister.getText().toString())){
            snackBarManager.showErrorSnackBar(mRoot, getString(R.string.error_confirm_password), Snackbar.LENGTH_LONG, null);
            return false;
        }
        user = new User(mEdtNameRegister.getText().toString(), mEdtEmailRegister.getText().toString(), mEdtPasswordRegister.getText().toString(), mSpnTypeUser.getSelectedItem().toString());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
