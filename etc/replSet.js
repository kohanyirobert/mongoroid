var config = {
  _id: "multi",
  members: [
    {_id: 0, host: "127.0.0.1:27018", priority: 1, tags: {notebook: "a"}},
    {_id: 1, host: "127.0.0.1:27019", priority: 1, tags: {notebook: "b"}},
    {_id: 2, host: "127.0.0.1:27020", priority: 2, tags: {notebook: "c"}}
  ],
  settings: {
    getLastErrorModes: {
      one: {notebook: 1},
      two: {notebook: 2},
      three: {notebook: 3}
    }
  }
}
rs.initiate(config);
