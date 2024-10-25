## **Uso do VSCode**
Devido a **problemas de hardware**, não foi possível utilizar o **Eclipse**. O projeto foi desenvolvido no **VSCode**, e por isso, foi preciso uma configuração específica para reconhecer a biblioteca **Gson**.

---
## **Configurações Necessárias**
### 1. **Configuração do VSCode**
Foi criada uma pasta chamada **`.vscode`** contendo o arquivo **`settings.json`** com o seguinte conteúdo:

```json
{
  "java.project.referencedLibraries": ["lib/**/*.jar"]
}
```
### 2. **Modificação do `.classpath`**
O arquivo **`.classpath`** foi modificado para reconhecer a biblioteca **Gson**:

``` xml
<classpath>
  <classpathentry kind="src" path="src"/>
  <classpathentry kind="lib" path="lib/gson-2.8.9.jar"/>
  <classpathentry kind="output" path="bin"/>
</classpath>
```
### 3. **Correção de Erro de Codificação**
Durante testes, foi encontrado um erro de codificação ao ler o arquivo **`fortune-br.txt`**:
``` graphql
  MalformedInputException: Input length = 1
```
**Solução:**
  O erro foi causado pela incompatibilidade na codificação do arquivo fortune-br.txt. Para corrigi-lo:
  1. Abrir o arquivo no Bloco de Notas e salvar como UTF-8.
  2. No código, garantimos a leitura correta:
``` java
    BufferedReader reader = Files.newBufferedReader(path, java.nio.charset.StandardCharsets.UTF_8);

```
