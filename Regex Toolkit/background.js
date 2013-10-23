/**
 * Listens for the app launching then creates the window
 *
 * @see http://developer.chrome.com/apps/app.window.html
 */
chrome.app.runtime.onLaunched.addListener(function() {
	chrome.app.window.create('window.html', {
  		"bounds": {
  			width: 650,
    		height: 475
  		}
  	});
});

chrome.runtime.onInstalled.addListener(function() {
        // When the app gets installed, set up the context menus
    chrome.contextMenus.create({
        title: CONTEXT_MENU_CONTENTS.forLauncher[0],
        id: 'launcher1',
        contexts: ['launcher']
    });
});
      