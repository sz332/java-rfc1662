Tasks

[ ] Refactor statemachine
	[ ] Every state is represented by an enum
	[ ] States are created together with the statemachine, not on the fly
	[ ] Check memory usage and efficiency

[ ] Write unit tests
	[ ] Empty stream
	[ ] Two good messages
	[ ] Trash before the good message
	[ ] Trash after the good message
	[ ] Trash before and after the good message
	[ ] Trash between two good messages
	[ ] Message containing a flag but wrong address
	[ ] Message containing a flag, address, but wrong control field
	[ ] Message containing a flag, address, control field, but wrong protocol
	[ ] Message containing a flag, address, control field, protocol, but no content
	[ ] Message containing a flag, address, control field, protocol, but no content
	