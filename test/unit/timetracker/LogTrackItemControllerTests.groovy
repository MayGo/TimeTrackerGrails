package timetracker



import org.junit.*
import grails.test.mixin.*

/**
 * LogTrackItemControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LogTrackItemController)
@Mock(LogTrackItem)
class LogTrackItemControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/logTrackItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.logTrackItemInstanceList.size() == 0
        assert model.logTrackItemInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.logTrackItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.logTrackItemInstance != null
        assert view == '/logTrackItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/logTrackItem/show/1'
        assert controller.flash.message != null
        assert LogTrackItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/logTrackItem/list'


        populateValidParams(params)
        def logTrackItem = new LogTrackItem(params)

        assert logTrackItem.save() != null

        params.id = logTrackItem.id

        def model = controller.show()

        assert model.logTrackItemInstance == logTrackItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/logTrackItem/list'


        populateValidParams(params)
        def logTrackItem = new LogTrackItem(params)

        assert logTrackItem.save() != null

        params.id = logTrackItem.id

        def model = controller.edit()

        assert model.logTrackItemInstance == logTrackItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/logTrackItem/list'

        response.reset()


        populateValidParams(params)
        def logTrackItem = new LogTrackItem(params)

        assert logTrackItem.save() != null

        // test invalid parameters in update
        params.id = logTrackItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/logTrackItem/edit"
        assert model.logTrackItemInstance != null

        logTrackItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/logTrackItem/show/$logTrackItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        logTrackItem.clearErrors()

        populateValidParams(params)
        params.id = logTrackItem.id
        params.version = -1
        controller.update()

        assert view == "/logTrackItem/edit"
        assert model.logTrackItemInstance != null
        assert model.logTrackItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/logTrackItem/list'

        response.reset()

        populateValidParams(params)
        def logTrackItem = new LogTrackItem(params)

        assert logTrackItem.save() != null
        assert LogTrackItem.count() == 1

        params.id = logTrackItem.id

        controller.delete()

        assert LogTrackItem.count() == 0
        assert LogTrackItem.get(logTrackItem.id) == null
        assert response.redirectedUrl == '/logTrackItem/list'
    }
}
