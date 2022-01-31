import org.hibernate.cfg.Configuration
import javax.persistence.*


@Entity
class Entry(
    @Column(nullable = false)
    val data: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
)

@Entity
class Person(

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = true)
    @OneToMany(cascade = [CascadeType.ALL])
    val entries: List<Entry>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
)

fun main(args: Array<String>) {
    println("Hello World!")

    val sessionFactory = Configuration()
        .addAnnotatedClass(Person::class.java)
        .addAnnotatedClass(Entry::class.java)
        .buildSessionFactory()

    val session = sessionFactory.openSession()
    session.beginTransaction()

    session.save(Person("bob"))
    session.save(Person("Marc"))
    session.save(Person("Antoine"))
    session.save(Person("Gérard"))

    session.transaction.commit()

    val result = session.createCriteria(Person::class.java).list()

    result.map { println( "${(it as Person).id} ${(it as Person).name}") }
}