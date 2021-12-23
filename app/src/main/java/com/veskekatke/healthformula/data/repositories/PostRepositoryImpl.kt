package com.veskekatke.healthformula.data.repositories

import android.content.SharedPreferences
import com.veskekatke.healthformula.data.datasources.local.post.PostDao
import com.veskekatke.healthformula.data.datasources.remote.post.PostService
import com.veskekatke.healthformula.data.models.post.Comment
import com.veskekatke.healthformula.data.models.post.Post
import com.veskekatke.healthformula.data.models.post.PostEntity
import com.veskekatke.healthformula.data.models.resource.Resource
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class PostRepositoryImpl(
    private val localDataSource: PostDao,
    private val remoteDataSource: PostService
) : PostRepository {

    val data = listOf(
        Post("1", "Steve Jobs", "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTIyzgZ90ZckAWqLVYb3EINTvMvrGTX2_TZwYqjo4BxXPUaGmOq", "Steve Jobs is the authorized self-titled biography of American business magnate and Apple co-founder Steve Jobs. The book was written at the request of Jobs by Walter Isaacson, a former executive at CNN and TIME who has written best-selling biographies of Benjamin Franklin and Albert Einstein.","Walter Isaacson", "2011",100, true,
            listOf(
                Comment("This book gave me a great insight into the life of the great Steve Jobs.", 5),
                Comment("Phenomenal book, I wouldn't read it twice though.", 4),
                Comment("Great book. I only wish the author dedicated more pages to Job's early life.", 3),
                Comment("The author have portrayed everything poorly. Couldn't get past page 123.", 2),
                Comment("Gosh I didn't know Steve Jobs was such a prick!!!!", 1)
            )
        ),
        Post("2", "Homo Deus", "https://www.laguna.rs/_img/korice/3897/homo_deus_kratka_istorija_sutrasnjice-juval_noa_harari_v.jpg", "Humans conquered the world thanks to their unique ability to believe in collective myths about gods, money, equality and freedom  as described in Sapiens: A Brief History of Humankind. In Homo Deus, Prof. Harari looks to the future and explores how global power might shift, as the principal force of evolution  natural selection  is replaced by intelligent design. What will happen to democracy when Google and Facebook come to know our likes and our political preferences better than we know them ourselves? What will happen to the welfare state when computers push humans out of the job market and create a massive new “useless class”? How might Islam handle genetic engineering? Will Silicon Valley end up producing new religions, rather than just novel gadgets? As Homo sapiens becomes Homo deus, what new destinies will we set for ourselves? As the self-made gods of planet earth, which projects should we undertake, and how will we protect this fragile planet and humankind itself from our own destructive powers? The book Homo Deus gives us a glimpse of the dreams and nightmares that will shape the 21st century.", "Yuval Noah Harari", "2015", 100, false, null),
        Post("3", "Sapiens", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTYY7n0e-GylssxKw9s-5EYUo65I3liFsIGEVo5ynV3PZngtaCV", "Sapiens: A Brief History of Humankind is a book by Yuval Noah Harari, first published in Hebrew in Israel in 2011 based on a series of lectures Harari taught at The Hebrew University of Jerusalem, and in English in 2014.","Yuval Noah Harari", "2015", 100, true, null)
    )

    var dataRecommended = listOf(
        Recommendation("2", "korisnik", "admin"),
        Recommendation("3", "korisnik", "admin"),
        Recommendation("1", "admin", "korisnik")
    ).toMutableList()

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.data.map {
                    PostEntity(
                        it._id,
                        it.name,
                        it.image,
                        it.content
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Post>> {
        return Observable.just(data)
//        return localDataSource
//            .getAll()
//            .map {
//                it.map {
//                    Post(it.id, it.name, it.image, it.content)
//                }
//            }
    }

    override fun getAllOnPromotion(): Observable<List<Post>> {
        return Observable.just(data.filter { it.promotion })
    }

    override fun getAllRecommendedToMe(username: String): Observable<List<Post>> {
        val recommendedToMe = dataRecommended.filter { it.userNameTo == username }

        return Observable.just(data.filter { it.id in recommendedToMe.map { it -> it.bookId }})
    }

    override fun addRecommendation(recommendation: Recommendation) {
        dataRecommended.add(recommendation)
    }
}

data class Recommendation (
    val bookId : String,
    val userNameTo : String,
    val userNameFrom : String
        )