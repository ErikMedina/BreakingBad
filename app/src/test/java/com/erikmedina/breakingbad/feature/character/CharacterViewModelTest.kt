package com.erikmedina.breakingbad.feature.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erikmedina.breakingbad.core.data.Character
import com.erikmedina.breakingbad.core.result.Error
import com.erikmedina.breakingbad.core.result.Status
import com.erikmedina.breakingbad.core.usecase.GetCharactersUseCase
import io.reactivex.Observable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CharacterViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule() //make the LiveData works synchronously
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var sut: CharacterViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = CharacterViewModel(getCharactersUseCase)
    }

    @Test
    fun `getCharacters when success then status is success and characters are returned`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(getCharactersSingle())
        // Act
        sut.getCharacters()
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.SUCCESS))
            assertThat(data[0], `is`(character1))
            assertThat(data[1], `is`(character2))
            assertThat(data[2], `is`(character3))
        }
    }

    @Test
    fun `getCharacters when error then status is Error with general error`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(Single.error(Throwable()))
        // Act
        sut.getCharacters()
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.ERROR))
            assertThat(error!!.type, `is`(Error.Type.GENERAL_ERROR))
        }
    }

    @Test
    fun `filterCharacterByName when query matches the name then characters matched are returned`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(getCharactersSingle())
        // Act
        sut.getCharacters()
        sut.filterCharacterByName("name")
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.SUCCESS))
            assertThat(data.size, `is`(2))
            assertThat(data[0], `is`(character1))
            assertThat(data[1], `is`(character2))
        }
    }

    @Test
    fun `filterCharacterByName when query doesn't match the name then 0 characters are returned`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(getCharactersSingle())
        // Act
        sut.getCharacters()
        sut.filterCharacterByName("query")
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.SUCCESS))
            assertThat(data.size, `is`(0))
        }
    }

    @Test
    fun `filterCharactersBySeason when season matches the appearance then status is success and characters are returned`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(getCharactersSingle())
        // Act
        sut.getCharacters()
        sut.filterCharactersBySeason("2")
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.SUCCESS))
            assertThat(data[0], `is`(character1))
            assertThat(data[1], `is`(character2))
        }
    }

    @Test
    fun `filterCharactersBySeason when season doesn't match the appearance then status is success and 0 characters are returned`() {
        // Arrange
        `when`(getCharactersUseCase.execute()).thenReturn(getCharactersSingle())
        // Act
        sut.getCharacters()
        sut.filterCharactersBySeason("4")
        // Assert
        sut.result.value?.run {
            assertThat(status, `is`(Status.SUCCESS))
            assertThat(data.size, `is`(0))
        }
    }

    ///////////////--------------- HELPERS -----------------------------////////////////////////

    private fun getCharactersSingle(): Single<List<Character>>? {


        return Single.fromObservable(Observable.just(characters))
    }

    private val character1 = Character(
        USER_ID_1,
        IMG_1,
        NAME_1,
        OCCUPATION_1,
        STATUS_1,
        NICKNAME_1,
        APPEARANCE_1
    )

    private val character2 = Character(
        USER_ID_2,
        IMG_2,
        NAME_2,
        OCCUPATION_2,
        STATUS_2,
        NICKNAME_2,
        APPEARANCE_2
    )

    private val character3 = Character(
        USER_ID_3,
        IMG_3,
        NAME_3,
        OCCUPATION_3,
        STATUS_3,
        NICKNAME_3,
        APPEARANCE_3
    )

    private val characters = listOf(character1, character2, character3)

    companion object {
        private const val USER_ID_1 = 1L
        private const val USER_ID_2 = 2L
        private const val USER_ID_3 = 3L

        private const val IMG_1 = "img1"
        private const val IMG_2 = "img2"
        private const val IMG_3 = "img3"

        private const val NAME_1 = "name1"
        private const val NAME_2 = "name2"
        private const val NAME_3 = "different3"

        private val OCCUPATION_1 = listOf("occupation1", "occupation2")
        private val OCCUPATION_2 = listOf("occupation1", "occupation2")
        private val OCCUPATION_3 = listOf("occupation1", "occupation2")

        private const val STATUS_1 = "status1"
        private const val STATUS_2 = "status2"
        private const val STATUS_3 = "status3"

        private const val NICKNAME_1 = "nickname1"
        private const val NICKNAME_2 = "nickname2"
        private const val NICKNAME_3 = "nickname3"

        private val APPEARANCE_1 = listOf(1, 2)
        private val APPEARANCE_2 = listOf(2, 3)
        private val APPEARANCE_3 = listOf(5)
    }
}
