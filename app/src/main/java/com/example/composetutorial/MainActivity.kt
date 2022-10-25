package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.SampleData.conversationSample
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        //determina o conteúdo da tela inicial
            ComposeTutorialTheme(darkTheme = true) {
            //Utilizar o tema escuro do JetPack Compose
                Conversation(SampleData.conversationSample)
                //Iniciar a função Conversation passando a variável conversationSample do objeto SampleData como parâmetro
            }
        }
    }
}

data class Message(val author: String, val body: String)
//classe de dados Mensagem recebe uma variável chamada autor(String) e uma variável chamada body(String)


@Composable
fun Conversation(messages: List<Message>){
//função conversation tem como parâmetro uma lista de mensagens
    LazyColumn{
    //organiza os itens numa lista vertical
        items(messages){ message ->
            //Itens da lista "messages" passam cada um como "message" e são mostrados pela função MessageCard()
            MessageCard(message)
        }
    }
}

@Composable
fun MessageCard(msg: Message){
//função MessageCard recebe como parâmetro uma mensagem
    Row (modifier = Modifier.padding(all = 8.dp)){
    //Organiza os elementos em linhas com 8 de padding
       Image(
            painter = painterResource(id = R.drawable.profile_picture),
           //Insere uma imagem de foto de perfil
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                //A imagem tem tamanho 40, em formato de círculo e uma borda
       )

        Spacer(modifier = Modifier.width(8.dp))
        //Adiciona um espaço de tamanho 8

        var isExpanded by remember{ mutableStateOf(false) }
        //variável "Está espandida" é preenchida lembrando do estado atual

        val surfaceColor by animateColorAsState(if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
        //valor da cor da superfície é primary se estiver espandida e secondary se estiver colapsada
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            //Organiza em colunas clicáveis que podem estar espandidas ou não
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shadowElevation = 1.dp, color = surfaceColor) {
                //O corpo das mensagens tem uma sombra e uma cor de superfície
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    //Se o corpo está espandido, o valor máximo das linhas é o maior valor possível, se estiver colapsado o máximo é 1
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    ComposeTutorialTheme(darkTheme = true) {
        Conversation(conversationSample)
        //Mostrar a amostra de conversa
    }
}

@Preview
@Composable
fun PreviewMessageCard(){
    ComposeTutorialTheme(darkTheme = true) {
        Surface {
            MessageCard(msg = Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!"))
        }
    }
}