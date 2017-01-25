[![Dependency Status](https://dependencyci.com/github/uneikov/gambling_station/badge)](https://dependencyci.com/github/uneikov/gambling_station)
[![Codacy branch grade](https://img.shields.io/codacy/grade/e27821fb6289410b8f58338c7e0bc686/master.svg?maxAge=2592000?style=plastic)]()
# gambling_station
<html>
  
<h2><a id="user-content-gambling-station-app" class="anchor" href="#gambling-station-app" aria-hidden="true"></a>Gambling Station Web Application</h2>

<p>Demonstrates the functionality of the Gambling Station online casino-like game:</p>

<h5>For admin role you can make following actions:</h5>

<ul>
<li>Add, enable/disable or remove users</li>
<li>Add, enable/disable or remove horses</li>
<li>See the races list with horses and stakes on the run</li>
<li>Make, edit and remove stakes</li>
<li>Edit admin profile</li>
</ul>

<h5>For regular user role you can make following actions:</h5>

<ul>
<li>Make, edit and remove stakes</li>
<li>List stakes with sorting by date, time and race results</li>
<li>Edit user profile</li>
</ul>

<h2><a id="user-content-to-get-the-code" class="anchor" href="#to-get-the-code" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>To get the code:</h2>

<p>Clone the repository:</p>

<pre><code>$ git clone git@github.com:uneikov/gambling_station.git
</code></pre>

<h2><a id="user-content-to-run-the-application" class="anchor" href="#to-test-the-application" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>To test the application:</h2>

<p>From the command line with Maven:</p>

<pre><code>$ cd gambling_station
$ mvn test
</code></pre>

<p>or</p>

<p>In your preferred IDE such as Intellij IDEA:</p>

<ul>
<li>Import gambling_station as a Maven Project</li>
<li>Deploy to tomcat</li>
</ul>

<p>Access the deployed web application at: http://localhost:8080/gamblingstation/</p>

<h2><a id="user-content-note" class="anchor" href="#note" aria-hidden="true"></a>Note:</h2>
<p>This application is deployed on heroku and you can taste it <a href="http://gamblingstation.herokuapp.com/">here</a></p>
<p>Application have scheduler to schedule three time intervals for gambling, race itself and service time and 
due to free heroku account limitation this app going to sleep after 30 minutes of inactivity, so if you want to
get full functionality, you need to wait until new hour starts.</p>

<p>By default all integration tests use heroku postgres database and:</p>

<ul>
<li>Tests starts and run slow</li>
<li>You can take exception "too many connections from user role[]... due to free account limitation</li>
</ul>

<p></p>

</html>

