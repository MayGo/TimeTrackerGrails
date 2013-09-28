package timetracker



import org.junit.*
import grails.test.mixin.*

@TestFor(StateTrackItemController)
@Mock(StateTrackItem)
class StateTrackItemControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/stateTrackItem/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.stateTrackItemInstanceList.size() == 0
        assert model.stateTrackItemInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.stateTrackItemInstance != null
    }

    void testSave() {
        controller.save()

        assert model.stateTrackItemInstance != null
        assert view == '/stateTrackItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/stateTrackItem/show/1'
        assert controller.flash.message != null
        assert StateTrackItem.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/stateTrackItem/list'

        populateValidParams(params)
        def stateTrackItem = new StateTrackItem(params)

        assert stateTrackItem.save() != null

        params.id = stateTrackItem.id

        def model = controller.show()

        assert model.stateTrackItemInstance == stateTrackItem
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/stateTrackItem/list'

        populateValidParams(params)
        def stateTrackItem = new StateTrackItem(params)

        assert stateTrackItem.save() != null

        params.id = stateTrackItem.id

        def model = controller.edit()

        assert model.stateTrackItemInstance == stateTrackItem
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/stateTrackItem/list'

        response.reset()

        populateValidParams(params)
        def stateTrackItem = new StateTrackItem(params)

        assert stateTrackItem.save() != null

        // test invalid parameters in update
        params.id = stateTrackItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/stateTrackItem/edit"
        assert model.stateTrackItemInstance != null

        stateTrackItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/stateTrackItem/show/$stateTrackItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        stateTrackItem.clearErrors()

        populateValidParams(params)
        params.id = stateTrackItem.id
        params.version = -1
        controller.update()

        assert view == "/stateTrackItem/edit"
        assert model.stateTrackItemInstance != null
        assert model.stateTrackItemInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/stateTrackItem/list'

        response.reset()

        populateValidParams(params)
        def stateTrackItem = new StateTrackItem(params)

        assert stateTrackItem.save() != null
        assert StateTrackItem.count() == 1

        params.id = stateTrackItem.id

        controller.delete()

        assert StateTrackItem.count() == 0
        assert StateTrackItem.get(stateTrackItem.id) == null
        assert response.redirectedUrl == '/stateTrackItem/list'
    }
}
