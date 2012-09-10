var config = {
  _id: "test",
  members: [
    {_id: 0, host: "127.0.0.1:27018", priority: 1, tags: {test: "27018"}},
    {_id: 1, host: "127.0.0.1:27019", priority: 1, tags: {test: "27019"}},
    {_id: 2, host: "127.0.0.1:27020", priority: 2, tags: {test: "27020"}}
  ],
  settings: {
    getLastErrorModes: {
      one: {test: 1},
      two: {test: 2},
      three: {test: 3}
    }
  }
}
rs.initiate(config);
