package pereira.juliane.construindosempre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.widget.Button;
import android.widget.TextView;

import pereira.juliane.construindosempre.Controller.DBHelper;

public class DisplayObras extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView Nome, Endereco, Data, Responsavel, Tipo;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_obras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Nome = (TextView) findViewById(R.id.txNome);
        Endereco = (TextView) findViewById(R.id.txEndereco);
        Data = (TextView) findViewById(R.id.txData);
        Responsavel = (TextView) findViewById(R.id.txResponsavel);
        Tipo = (TextView) findViewById(R.id.txTipo);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //significa que esta é a parte de visualização não a parte de contato adicionar
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nome = rs.getString(rs.getColumnIndex(DBHelper.OBRAS_COLUMN_NOME));
                String endereco = rs.getString(rs.getColumnIndex(DBHelper.OBRAS_COLUMN_ENDERECO));
                String responsavel = rs.getString(rs.getColumnIndex(DBHelper.OBRAS_COLUMN_RESPONSAVEL));
                String tipo = rs.getString(rs.getColumnIndex(DBHelper.OBRAS_COLUMN_TIPO));
                String data = rs.getString(rs.getColumnIndex(DBHelper.OBRAS_COLUMN_DATA));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.btSalvar);
                b.setVisibility(View.INVISIBLE);

                Nome.setText((CharSequence) nome);
                Nome.setFocusable(false);
                Nome.setClickable(false);

                Endereco.setText((CharSequence) endereco);
                Endereco.setFocusable(false);
                Endereco.setClickable(false);

                Data.setText((CharSequence) responsavel);
                Data.setFocusable(false);
                Data.setClickable(false);

                Responsavel.setText((CharSequence) tipo);
                Responsavel.setFocusable(false);
                Responsavel.setClickable(false);

                Tipo.setText((CharSequence) data);
                Tipo.setFocusable(false);
                Tipo.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //nenhuma inspeção Simplificável
        if (id == R.id.Edit_Contact) {
            Button b = (Button) findViewById(R.id.btSalvar);
            b.setVisibility(View.VISIBLE);
            Nome.setEnabled(true);
            Nome.setFocusableInTouchMode(true);
            Nome.setClickable(true);

            Endereco.setEnabled(true);
            Endereco.setFocusableInTouchMode(true);
            Endereco.setClickable(true);

            Data.setEnabled(true);
            Data.setFocusableInTouchMode(true);
            Data.setClickable(true);

            Responsavel.setEnabled(true);
            Responsavel.setFocusableInTouchMode(true);
            Responsavel.setClickable(true);

            Tipo.setEnabled(true);
            Tipo.setFocusableInTouchMode(true);
            Tipo.setClickable(true);

            return true;
        } else if (id == R.id.Delete_Contact) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.deleteContact)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mydb.deleteObra(id_To_Update);
                            Toast.makeText(getApplicationContext(), "Apagado com sucesso",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Usuário cancelou a caixa de diálogo
                        }
                    });

            AlertDialog d = builder.create();
            d.setTitle("Você tem certeza?");
            d.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //função cadastro
    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateObra(id_To_Update, Nome.getText().toString(),
                        Endereco.getText().toString(), Data.getText().toString(),
                        Responsavel.getText().toString(), Tipo.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Atualizado com exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertObra(Nome.getText().toString(), Endereco.getText().toString(),
                        Data.getText().toString(), Responsavel.getText().toString(),
                        Tipo.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Salvo com exito",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao salvar",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
