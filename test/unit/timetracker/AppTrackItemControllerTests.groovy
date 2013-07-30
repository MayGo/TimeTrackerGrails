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
        assert "/applicationInfo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.applicationInfoInstanceList.size() == 0
        assert model.applicationInfoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.applicationInfoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.applicationInfoInstance != null
        assert view == '/applicationInfo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/applicationInfo/show/1'
        assert controller.flash.message != null
        assert AppTrackItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/applicationInfo/list'

        populateValidParams(params)
        def applicationInfo = new AppTrackItem(params)

        assert applicationInfo.save() != null

        params.id = applicationInfo.id

        def model = controller.show()

        assert model.applicationInfoInstance == applicationInfo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/applicationInfo/list'

        populateValidParams(params)
        def applicationInfo = new AppTrackItem(params)

        assert applicationInfo.save() != null

        params.id = applicationInfo.id

        def model = controller.edit()

        assert model.applicationInfoInstance == applicationInfo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/applicationInfo/list'

        response.reset()

        populateValidParams(params)
        def applicationInfo = new AppTrackItem(params)

        assert applicationInfo.save() != null

        // test invalid parameters in update
        params.id = applicationInfo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/applicationInfo/edit"
        assert model.applicationInfoInstance != null

        applicationInfo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/applicationInfo/show/$applicationInfo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        applicationInfo.clearErrors()

        populateValidParams(params)
        params.id = applicationInfo.id
        params.version = -1
        controller.update()

        assert view == "/applicationInfo/edit"
        assert model.applicationInfoInstance != null
        assert model.applicationInfoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/applicationInfo/list'

        response.reset()

        populateValidParams(params)
        def applicationInfo = new AppTrackItem(params)

        assert applicationInfo.save() != null
        assert AppTrackItem.count() == 1

        params.id = applicationInfo.id

        controller.delete()

        assert AppTrackItem.count() == 0
        assert AppTrackItem.get(applicationInfo.id) == null
        assert response.redirectedUrl == '/applicationInfo/list'
    }
}
