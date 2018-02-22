<template>
  <div id="listtrees">
    <h2>List of Trees</h2>
    <table>
      <tr>
          <td>ID</td>
          <td>Species Type</td>
	  <td>Status</td>
      </tr>
      <tr>
          <td>
              <input type="text" placeholder="Species">
          </td>
	  <td>
              <input type="text" placeholder="Latitude">
          </td>
<td>
              <input type="text" placeholder="Longitude">
          </td>
          <td>
              <button>Create tree</button>
          </td>
      </tr>
    </table>
    <p>
      <span style="color:red">Error: Message text comes here</span>
    </p>
  </div>
</template>