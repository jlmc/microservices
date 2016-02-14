# Herança, Principio de Liskov, @Specializes

Principio de Liskov (principio da substituição); no ponto de vista do Java, se estivermos usando interfaces, conseguimos trocar suas implementações ou conseguimos trocar um tipo por seus subtipos. Usando CDI, no entanto, não basta usar implements ou extends.

Vamos tomar como exemplo a classe <b>SalariesPlanCalculator2013</b> e a classe <b>SalariesPlanCalculator2013Asynchronous</b>. O importante é entendermos como aplicar a substituição entre a calculadora e a calculadora assíncrona, seu subtipo.

```

	@TaxCal
	public class SalariesPlanCalculator2013 implements SalariesPlanCalculator {
	
		@Override
		public double earningsEstimates(final Employeer employee) {
			System.out.println("SalariesPlanCalculator2013");
			return 5;
	}
```

Para enriquecer nossa análise, adicionamos um qualificador novo, que usaremos apenas nesse exemplo: 

<b>@TaxCal</b>.

Agora vejamos a calculadora assíncrona.


```

	@Alternative
	@Priority(javax.interceptor.Interceptor.Priority.APPLICATION)
	public class SalariesPlanCalculator2013Asynchronous extends SalariesPlanCalculator2013 {
		
		@Override
		public double earningsEstimates(final Employeer employee) {
			System.out.println("SalariesPlanCalculator2013Asynchronous");
			return 10;
		}
	
	}
```

###conseguimos predizer como a CDI irá resolver a seguinte dependência:

```

	@Inject
	private SalariesPlanCalculator calculator;
```


Como ambas as classes que acabamos de ver são de tipo compatível com a interface solicitada, mas como a
SalariesPlanCalculator2013Asynchronous é alternativa, ou seja, tem prioridade, sabemos que ela será a implementação selecionada.

Mas qual seria o resultado se o trecho que solicita a injeção da dependência fosse como o seguinte:

```

	@Inject @TaxCal
	private SalariesPlanCalculator calculator;
```

Nesse caso, seria injetada uma instância da classe SalariesPlanCalculator2013, pois sua subclasse não possui o qualificador <b>@TaxCal</b>. 

No entanto por mais que não tenhamos analisado o código da classe <b>SalariesPlanCalculator2013Asynchronous</b>, vamos considerar que ela é uma evolução de sua classe mãe, e que, ao criá-la, esperávamos que ela entrasse no lugar da classe mãe (Principio de Liskov )  sabemos que é possível.
 
Mas como nos assegurar de que a nova classe sempre será selecionada no lugar da antiga?

Uma forma de fazer isso seria, além de estender a classe original, colocar na subclasse todos os qualificadores da primeira. Com isso, no exemplo que acabamos de ver, em que além do tipo eram pedidos qualificadores (no caso um, mas poderiam ser diversos), o bean selecionado seria a calculadora assíncrona.
Mesmo assim ainda teríamos dois casos em que a classe mãe continuaria sendo utilizada: 

<b>Métodos produtores e observadores de eventos.</b> 

Esses dois assuntos ainda não foram vistos, mas o que importa por enquanto é que ambos não são herdados pela subclasse.


#A anotação @Specializes

Para resolver essa questão, a CDI disponibiliza a anotação @Specializes, que marca a subclasse como uma substituta da classe mãe. Assim, mesmo que a nova implementação seja adicionada em um novo jar, muito tempo depois que o sistema está em produção, ela certamente entrará em todos os lugares onde a classe mãe era utilizada. Para tal, basta colocar essa anotação na classe filha.

```

	@Specializes @Assincr
	public class SalariesPlanCalculator2013Asynchronous extends SalariesPlanCalculator2013 {
	
		@Override
		public double earningsEstimates(final Employeer employee) {
			System.out.println("SalariesPlanCalculator2013Asynchronous");
			return 10;
		}

	}
```


Agora sabemos que o novo bean substituirá totalmente o antigo, pois ao usar <b>@Specializes</b> a classe filha herda também todos os qualificadores da mãe. Isso não impede, no entanto, que a nova classe defina novos qualificadores, como o @Assincr do exemplo.

Outra coisa herdada é o nome do bean original. Caso a classe mãe possua o qualificador @Named, a classe filha necessariamente herdará o mesmo nome. Se a classe mãe não possuir um nome, a filha fica livre para definir ou não um. Mas se a mãe for nomeada e a filha tentar definir um novo nome, será lançado um erro quando a configuração for lida enquanto a aplicação inicializa.

Porém, nem tudo é tão automático, como o mecanismo de especialização precisa garantir que realmente a classemãe não será instanciada em momento algum pelo CDI, e como os métodos produtores e observadores de eventos não são herdados, os mesmos ficarão inativos a menos que sejam reimplementados (sobrescritos) na subclasse.


