package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import org.acme.clients.BrasilApiClient;
import org.acme.models.Feriado;
import org.acme.utils.AjusteData;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class FeriadoService {
    @Inject
    @RestClient
    BrasilApiClient brasilApiClient;

    public List<Feriado> getTodosFeriados(String ano) {

        String path = getMethodPath("getFeriados");
        System.out.println(path);
        return brasilApiClient.getFeriados(ano);
    }

    public String checkFeriado(String ano, String data) {
        LocalDate localDate = AjusteData.convertTextToLocalDate(data);
        List<Feriado> feriados = getTodosFeriados(ano);

        return feriados.stream()
                .filter(feriado -> feriado.getDate().equals(localDate.toString()))
                .findFirst()
                .map(feriado -> "A data: " + data.trim() + " é um feriado:\n " + feriado)
                .orElse("A data nao é um feriado");
    }

    public String getMethodPath(String methodName) {
        String basePath = ConfigProvider.getConfig().getValue("quarkus.rest-client.brasil-api.url", String.class);
        Method[] methods = BrasilApiClient.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Path path = method.getAnnotation(Path.class);
                if (path != null) {
                    return basePath + path.value();
                }
            }
        }
        return "Método não encontrado ou sem anotação Path";
    }
}



/*
Passo a Passo do Uso da Stream
1. feriados.stream()
O que faz?
Este comando converte a lista feriados em um Stream. Um Stream é uma sequência de elementos que suporta operações sequenciais e paralelas.
Por que é usado?
Usar Stream permite aplicar uma série de operações de maneira eficiente e expressiva, como filtragem, mapeamento, redução, e mais.

2. .filter(feriado -> feriado.getDate().equals(localDate.toString()))
O que faz?
A operação filter aplica um predicado (uma função que retorna um valor booleano) a cada elemento do Stream.
Neste caso, o predicado é feriado -> feriado.getDate().equals(localDate.toString()), que verifica se a data do feriado é igual à localDate convertida em string.

Por que é usado?
Para restringir o conjunto de elementos no Stream apenas aos que satisfazem a condição, ou seja, aqueles cuja data é igual à localDate especificada.

3. .findFirst()
O que faz?
Esta operação retorna um Optional descrevendo o primeiro elemento do Stream que atende ao predicado, ou um Optional vazio se nenhum elemento atende ao predicado.
A busca para assim que um elemento é encontrado que satisfaz o filtro, o que pode melhorar a performance ao evitar processamento desnecessário.

Por que é usado?
Para tentar encontrar rapidamente um feriado que corresponda à data especificada sem necessitar verificar todos os elementos se um já for encontrado.

4. .map(feriado -> "A data: " + data.trim() + " é um feriado:\n " + feriado)
O que faz?
A operação map transforma o elemento dentro do Optional (se presente).
Neste caso, se um feriado é encontrado, ele é transformado em uma string formatada que descreve o feriado.

Por que é usado?
Para converter o objeto feriado (caso exista) em uma mensagem formatada que será exibida ao usuário.

5. .orElse("A data nao é um feriado")
O que faz?
Esta operação é chamada no Optional resultante da cadeia de operações anteriores. Se o Optional contém um valor (um feriado foi encontrado), retorna esse valor. Se o Optional está vazio (nenhum feriado correspondeu), retorna a string "A data nao é um feriado".
Por que é usado?
Para fornecer uma resposta padrão caso nenhum feriado corresponda à data pesquisada, garantindo que sempre haverá uma resposta definida, seja uma confirmação de feriado ou uma negação.
 */