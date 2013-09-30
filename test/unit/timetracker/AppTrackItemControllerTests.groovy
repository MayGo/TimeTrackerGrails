package timetracker



import org.junit.*
import grails.test.mixin.*

@TestFor(AppTrackItemController)
@Mock(AppTrackItem)
class AppTrackItemControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/appTrackItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.appTrackItemInstanceList.size() == 0
        assert model.appTrackItemInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.appTrackItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.appTrackItemInstance != null
        assert view == '/appTrackItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/appTrackItem/show/1'
        assert controller.flash.message != null
        assert AppTrackItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/appTrackItem/list'

        populateValidParams(params)
        def appTrackItem = new AppTrackItem(params)

        assert appTrackItem.save() != null

        params.id = appTrackItem.id

        def model = controller.show()

        assert model.appTrackItemInstance == appTrackItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/appTrackItem/list'

        populateValidParams(params)
        def appTrackItem = new AppTrackItem(params)

        assert appTrackItem.save() != null

        params.id = appTrackItem.id

        def model = controller.edit()

        assert model.appTrackItemInstance == appTrackItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/appTrackItem/list'

        response.reset()

        populateValidParams(params)
        def appTrackItem = new AppTrackItem(params)

        assert appTrackItem.save() != null

        // test invalid parameters in update
        params.id = appTrackItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/appTrackItem/edit"
        assert model.appTrackItemInstance != null

        appTrackItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/appTrackItem/show/$appTrackItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        appTrackItem.clearErrors()

        populateValidParams(params)
        params.id = appTrackItem.id
        params.version = -1
        controller.update()

        assert view == "/appTrackItem/edit"
        assert model.appTrackItemInstance != null
        assert model.appTrackItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/appTrackItem/list'

        response.reset()

        populateValidParams(params)
        def appTrackItem = new AppTrackItem(params)

        assert appTrackItem.save() != null
        assert AppTrackItem.count() == 1

        params.id = appTrackItem.id

        controller.delete()

        assert AppTrackItem.count() == 0
        assert AppTrackItem.get(appTrackItem.id) == null
        assert response.redirectedUrl == '/appTrackItem/list'
    }
}
