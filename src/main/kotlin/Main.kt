import org.flywaydb.core.Flyway
import org.hibernate.cfg.Configuration
import java.util.*
import javax.persistence.*
import javax.persistence.criteria.CriteriaQuery

@Entity
class Entry(
    @Column(nullable = false)
    val data: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
)

@Entity
class Person(

    @Column(unique = true, nullable = false)
    val name: String,

    @Column(nullable = true)
    @OneToMany(cascade = [CascadeType.ALL])
    val entries: List<Entry>? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "created_at")
    val createdAt : Date? = null,

    @Column(name = "updated_at")
    val updatedAt : Date? = null,
)

fun main(args: Array<String>) {

    ///
    //    Setup DB
    val flyway = Flyway
        .configure()
        .dataSource("jdbc:sqlite:test.db", null, null).load()
    flyway.migrate()

    ////

    val sessionFactory = Configuration()
        .addAnnotatedClass(Person::class.java)
        .addAnnotatedClass(Entry::class.java)
        .buildSessionFactory()

    val session = sessionFactory.openSession()
    session.beginTransaction()

    session.save(Person("bob"))
    session.save(Person("Marc"))
    session.save(Person("Antoine", entries = listOf(Entry("Just met him online once"))))
    session.save(Person("Gérard", entries = listOf(Entry("birthday 12/6"), Entry("Met on Twitch"))))

    session.transaction.commit()

    val criteria: CriteriaQuery<Person> = session
        .criteriaBuilder
        .createQuery(Person::class.java)
    criteria.from(Person::class.java)
    val result = session.createQuery(criteria).list()


    result.map { println( "${(it as Person).entries} ${it.name}") }
    result.map { println("${(it as Person).entries?.size}") }
}