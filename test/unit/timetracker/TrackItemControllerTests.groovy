package timetracker



import org.junit.*
import grails.test.mixin.*

/**
 * TrackItemControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(TrackItemController)
@Mock(TrackItem)
class TrackItemControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/trackItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.trackItemInstanceList.size() == 0
        assert model.trackItemInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.trackItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.trackItemInstance != null
        assert view == '/trackItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/trackItem/show/1'
        assert controller.flash.message != null
        assert TrackItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/trackItem/list'


        populateValidParams(params)
        def trackItem = new TrackItem(params)

        assert trackItem.save() != null

        params.id = trackItem.id

        def model = controller.show()

        assert model.trackItemInstance == trackItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/trackItem/list'


        populateValidParams(params)
        def trackItem = new TrackItem(params)

        assert trackItem.save() != null

        params.id = trackItem.id

        def model = controller.edit()

        assert model.trackItemInstance == trackItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/trackItem/list'

        response.reset()


        populateValidParams(params)
        def trackItem = new TrackItem(params)

        assert trackItem.save() != null

        // test invalid parameters in update
        params.id = trackItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/trackItem/edit"
        assert model.trackItemInstance != null

        trackItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/trackItem/show/$trackItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        trackItem.clearErrors()

        populateValidParams(params)
        params.id = trackItem.id
        params.version = -1
        controller.update()

        assert view == "/trackItem/edit"
        assert model.trackItemInstance != null
        assert model.trackItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/trackItem/list'

        response.reset()

        populateValidParams(params)
        def trackItem = new TrackItem(params)

        assert trackItem.save() != null
        assert TrackItem.count() == 1

        params.id = trackItem.id

        controller.delete()

        assert TrackItem.count() == 0
        assert TrackItem.get(trackItem.id) == null
        assert response.redirectedUrl == '/trackItem/list'
    }
}
